package org.openbox.dashboard;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.moonlightcontroller.aggregator.IApplicationAggregator;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.managers.models.ConnectionInstance;
import org.moonlightcontroller.managers.models.messages.Message;
import org.moonlightcontroller.registry.ApplicationRegistry;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.registry.RegisteredBoxApplication;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.ITopologyManager;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.moonlightcontroller.topology.Segment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.openbox.dashboard.GraphsUtils.toPrettyGraph;
import static org.openbox.dashboard.GraphsUtils.toPrettyStatement;

public class NetworkInformationService {
    private static NetworkInformationService ourInstance = new NetworkInformationService();
    private List<Map> apps;
    private List<Map> prettyAggregated;
    private Map<String, List> topologyGraph;



    public static NetworkInformationService getInstance() {
        return ourInstance;
    }
    private final static Logger LOG = Logger.getLogger(NetworkInformationService.class.getName());

    private IApplicationAggregator aggregated;
    private ITopologyManager topology;

    private NetworkInformationService() {
    }

    public List<Map> getApps() {
        return apps;
    }

    public List<Map> getAggregated() {
        return prettyAggregated;
    }

    public void onPostAggregation(IApplicationRegistry applicationRegistry, IApplicationAggregator aggregated) {

        this.aggregated = aggregated;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        List<Map> apps = new ArrayList<>();
        applicationRegistry.getApplications().forEach((registeredBoxApplication) -> {
            BoxApplication app = registeredBoxApplication.getApplication();
            HashMap<String, Object> prettyApp = new HashMap<>();
            prettyApp.put("name", registeredBoxApplication.getName());
            prettyApp.put("jarName", registeredBoxApplication.getJarName());

            // Statements
            List<Map> statements = new ArrayList<>();
            app.getStatements().stream().forEach((s) -> {

                Map<String, Object> prettyStatement = toPrettyStatement(s);
                statements.add(prettyStatement);

            });

            prettyApp.put("statements", statements);
            prettyApp.put("priority", app.getPriority());

            apps.add(prettyApp);
        });

        this.apps = apps;


        List<Map> prettyAggregated = new ArrayList<>();
        this.aggregated.getAggregated().forEach((location, graph) -> {
            Map<String, Object> prettyLocation = new HashMap<>();
            prettyLocation.put("location", location);
            prettyLocation.put("processingGraph", toPrettyGraph(graph));
            prettyAggregated.add(prettyLocation);
        });

        this.prettyAggregated = prettyAggregated;
        try {
            FileWriter fileWriter = new FileWriter("./apps.json");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(mapper.writeValueAsString(apps));
            printWriter.close();

            fileWriter = new FileWriter("./aggregated.json");
            printWriter = new PrintWriter(fileWriter);
            printWriter.print(mapper.writeValueAsString(prettyAggregated));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setTopology(ITopologyManager topology) {

        this.topology = topology;

        topologyGraph = new HashMap<>();
        ArrayList<Object> nodes = new ArrayList<>();
        ArrayList<Object> links = new ArrayList<>();
        topologyGraph.put("nodes", nodes);
        topologyGraph.put("links", links);

        topology.bfs().stream().filter((b) -> b instanceof Segment).iterator().forEachRemaining((segment) -> {
            addSegment(segment);
            ((Segment)segment).getDirectSegments().forEach((c) -> this.addLink(segment, c));
        });

        SouthboundProfiler.getInstance().onTopologyUpdate(this.topologyGraph);

    }

    public ITopologyManager getTopology() {
        return topology;
    }

    private void addLink(ILocationSpecifier src, ILocationSpecifier dst) {
        HashMap<String, Object> link = new HashMap<>();
        link.put("source", toBlockId(src));
        link.put("target", toBlockId(dst));

        topologyGraph.get("links").add(link);
    }

    private void addSegment(ILocationSpecifier block) {
        HashMap<String, Object> node = new HashMap<>();
        node.put("id", toBlockId(block));
        node.put("label", toBlockLabel(block));

        topologyGraph.get("nodes").add(node);

        SouthboundProfiler.getInstance().onTopologyUpdate(this.topologyGraph);

    }

    private String toBlockId(ILocationSpecifier block) {
        if (!block.isSingleLocation())
            return "S" + block.getId();
        else
            return "OBI" + block.getId();
    }

    private String toBlockLabel(ILocationSpecifier block) {
        if (!block.isSingleLocation())
            return "Segment " + block.getId();
        else
            return "OBI " + block.getId();
    }

    public Map<String, List> getTopologyGraph() {
        return topologyGraph;
    }

    public void addOBI(ConnectionInstance connectionInstance) {

        long dpid = connectionInstance.getDpid();
        removeOBI(dpid);


        // add obi node
        String obiId = toBlockId(new InstanceLocationSpecifier(dpid));

        Map<String, Object> newObi = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        newObi.put("id", obiId);
        newObi.put("label", "OBI " + dpid);
        newObi.put("dpid", dpid);
        properties.put("processingGraphReceived", false);
        newObi.put("properties", properties);

        addOBIToTopologyGraph(dpid, newObi);

    }

    private void addOBIToTopologyGraph(long dpid, Map<String, Object> newObi) {
        topologyGraph.get("nodes").add(newObi);

        // add segment -> obi link
        InstanceLocationSpecifier obiLocationSpecifier = new InstanceLocationSpecifier(dpid);
        addLink(topology.getSegmentByEndpoint(dpid), obiLocationSpecifier);
        SouthboundProfiler.getInstance().onTopologyUpdate(this.topologyGraph);
    }

    public void updateOBI(Long dpid, boolean processingGraphReceived) {
        Map<String, Object> obi = getOBI(dpid);
        if (obi == null) {
            LOG.warning("Received updateOBI request for unkown obi with dpid="+dpid);
            return;
        }

        ((Map<String, Object>) obi.get("properties")).put("processingGraphReceived", processingGraphReceived);

        SouthboundProfiler.getInstance().onTopologyUpdate(this.topologyGraph);
    }

    public void removeOBI(Long dpid) {
        topologyGraph.put("nodes", (List<Map>) topologyGraph.get("nodes").stream()
                .filter((n) -> !dpid.equals(((Map<String, Object>) n).get("dpid"))).collect(Collectors.toList()));

        String id = "OBI"+dpid;
        topologyGraph.put("links", (List<Map>) topologyGraph.get("links").stream()
                .filter((n) -> !id.equals(((Map<String, Object>) n).get("target"))).collect(Collectors.toList()));

        SouthboundProfiler.getInstance().onTopologyUpdate(this.topologyGraph);

    }

    public Map<String, Object> getOBI(Long dpid) {
        List<Object> match = (List<Object>) topologyGraph.get("nodes").stream().filter((n) -> dpid.equals(((Map<String, Object>) n).get("dpid"))).collect(Collectors.toList());
        if (match.size() != 1)
            return null;

        return ((Map<String, Object>) match.get(0));
    }

}
