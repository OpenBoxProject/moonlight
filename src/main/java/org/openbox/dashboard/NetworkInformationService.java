package org.openbox.dashboard;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.aggregator.IApplicationAggregator;
import org.moonlightcontroller.managers.models.messages.Message;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.ITopologyManager;
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

    private ApplicationAggregator aggregated;
    private ITopologyManager topology;

    private NetworkInformationService() {
    }

    public List<Map> getApps() {
        return apps;
    }

    public List<Map> getAggregated() {
        return prettyAggregated;
    }

    public void setAggregated(IApplicationAggregator aggregated) {

        this.aggregated = (ApplicationAggregator) aggregated;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        List<Map> apps = new ArrayList<>();
        this.aggregated.getApps().forEach((app) -> {
            HashMap<String, Object> prettyApp = new HashMap<String, Object>();
            prettyApp.put("name", app.getName());

            // Statements
            List<Map> statements = new ArrayList<Map>();
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

    public List<Map> getNetworkInformation(IProcessingGraph graph) {
        return null;
    }

    public void setTopology(ITopologyManager topology) {

        topologyGraph = new HashMap<>();
        ArrayList<Object> nodes = new ArrayList<>();
        ArrayList<Object> links = new ArrayList<>();
        topologyGraph.put("nodes", nodes);
        topologyGraph.put("links", links);

        this.topology = topology;
        topology.bfs().iterator().forEachRemaining((block) -> {
            addBlock(block);
            if (block instanceof Segment) {
                ArrayList<ILocationSpecifier> children = new ArrayList<>();
                children.addAll(((Segment)block).getDirectEndpoints());
                children.addAll(((Segment)block).getDirectSegments());
                children.forEach((c) -> this.addLink(block, c));
            }
        });
    }

    private void addLink(ILocationSpecifier src, ILocationSpecifier dst) {
        HashMap<String, Object> link = new HashMap<>();
        link.put("source", toBlockId(src));
        link.put("target", toBlockId(dst));

        topologyGraph.get("links").add(link);
    }

    private void addBlock(ILocationSpecifier block) {
        HashMap<String, Object> node = new HashMap<>();
        node.put("id", toBlockId(block));
        node.put("label", toBlockLabel(block));

        if ((String.valueOf(node.get("id")).startsWith("OBI")))
        node.put("dpid", block.getId());

        topologyGraph.get("nodes").add(node);

    }

    private String toBlockId(ILocationSpecifier block) {
        if (!block.isSingleLocation())
            return "S" + block.getId();
        else
            return "E" + block.getId();
    }

    private String toBlockLabel(ILocationSpecifier block) {
        if (!block.isSingleLocation())
            return "Segment " + block.getId();
        else
            return "Endpoint " + block.getId();
    }

    public Map<String, List> getTopologyGraph() {
        return topologyGraph;
    }

    public void addOBI(long dpid, Message message) {

        removeOBI(dpid);

        String address = message.getSourceAddr();

        // add obi node
        Map<String, Object> newObi = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        String obiId = "OBI" + dpid;

        newObi.put("id", obiId);
        newObi.put("label", "OBI " + dpid);
        newObi.put("dpid", dpid);
        properties.put("address", address);
        properties.put("processingGraphReceived", false);
        newObi.put("properties", properties);

        topologyGraph.get("nodes").add(newObi);

        // add endpoint->obi link
        HashMap<String, Object> link = new HashMap<>();
        link.put("source", "E"+dpid);
        link.put("target", obiId);

        topologyGraph.get("links").add(link);

    }

    public void updateOBI(Long dpid, boolean processingGraphReceived) {
        Map<String, Object> obi = getOBI(dpid);
        if (obi == null) {
            LOG.warning("Received updateOBI request for unkown obi with dpid="+dpid);
            return;
        }

        ((Map<String, Object>) obi.get("properties")).put("processingGraphReceived", processingGraphReceived);
    }

    public void removeOBI(Long dpid) {
        topologyGraph.put("nodes", (List<Map>) topologyGraph.get("nodes").stream()
                .filter((n) -> !dpid.equals(((Map<String, Object>) n).get("dpid"))).collect(Collectors.toList()));

        String id = "OBI"+dpid;
        topologyGraph.put("links", (List<Map>) topologyGraph.get("links").stream()
                .filter((n) -> !id.equals(((Map<String, Object>) n).get("id"))).collect(Collectors.toList()));

    }

    public Map<String, Object> getOBI(Long dpid) {
        List<Object> match = (List<Object>) topologyGraph.get("nodes").stream().filter((n) -> dpid.equals(((Map<String, Object>) n).get("dpid"))).collect(Collectors.toList());
        if (match.size() != 1)
            return null;

        return ((Map<String, Object>) match.get(0));
    }

}
