package org.openbox.dashboard;

import com.google.gson.Gson;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.eclipse.jetty.util.ArrayQueue;
import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

@Service
public class SouthboundProfiler {
    private static SouthboundProfiler ourInstance;
    private List<Map<String, Object>> obis = new ArrayList<>();
    private Queue<Map<String, Object>> messages = new ArrayQueue<>(100);

    public static SouthboundProfiler getInstance() {
        return ourInstance; // singleton instance is initialized by spring
    }
    private final static Logger LOG = Logger.getLogger(SouthboundProfiler.class.getName());
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private SimpMessagingTemplate template;

    private SouthboundProfiler() {
        ourInstance = this;
    }

    public List<Map<String, Object>> getObis() {
        return obis;
    }

    public void addOBI(Hello message, SetProcessingGraphRequest setProcessingGraphRequest) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        Map<String, Object> obi = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        Map<String, Object> graph = new HashMap<>();

        // remove old appearances
        obis = obis.stream().filter((o) -> (Long) o.get("dpid") != setProcessingGraphRequest.getDpid()).collect(toList());

        properties.put("dpid", setProcessingGraphRequest.getDpid());
        properties.put("address", message.getSourceAddr());
        properties.put("modules", setProcessingGraphRequest.getModules());
        properties.put("capabilities", message.getCapabilities());
        properties.put("type", message.getObiType());
        properties.put("version", message.getVersion());
        properties.put("processingGraphReceived", false);

        graph.put("blocks", setProcessingGraphRequest.getBlocks().stream().map((b) -> {
            HashMap<String, Object> block = new HashMap<>();
            block.put("id", b.getName());
            block.put("type", b.getType());
            block.put("config", b.getConfig());
            return block;
        }).collect(toList()));

        graph.put("connectors", setProcessingGraphRequest.getConnectors().stream().map((l) -> {

                HashMap<String, String> link = new HashMap<>();
                link.put("sourceId", l.getSrc());
                link.put("sourcePort", String.valueOf(l.getSrc_port()));

                link.put("destinationId", l.getDst());
                return link;

            }).collect(toList()));

        obi.put("dpid", setProcessingGraphRequest.getDpid());
        obi.put("properties", properties);
        obi.put("processingGraph", graph);

        this.obis.add(obi);
        NetworkInformationService.getInstance().addOBI(setProcessingGraphRequest.getDpid(), message);
        try {
            FileWriter fileWriter = new FileWriter("./obis.json");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(mapper.writeValueAsString(obis.get(0)));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onSetProcessingResponse(long dpid) {
        Map<String, Object> obi = null;
        for (Map<String, Object> o: obis) {
            if ((o.get("dpid")).equals(dpid)) {
                obi = o;
                break;
            }
        }

        ((Map<String, Object>)obi.get("properties")).put("processingGraphReceived", true);
        NetworkInformationService.getInstance().updateOBI(dpid, true);

    }

    public void onMessage(IMessage message, Boolean incoming) {

        Map<String, Object> msg = new HashMap<>();
        msg.put("time", now());
        msg.put("direction", incoming ? "IN" : "OUT");
        msg.put("message", message);
        msg.put("dpid", message.getDpid());
        if (messages.size() == 100)
            messages.remove();

        messages.add(msg);

//        LOG.info(messages.toString());
        this.template.convertAndSend("/topic/messages", new Gson().toJson(msg));
    }

    public void onTopologyUpdate(Map<String, List> topologyManager) {
        this.template.convertAndSend("/topic/topology", new Gson().toJson(topologyManager));
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    public Map<String, Object> getObi(Long dpid) {
        List<Map> match = this.obis.stream().filter((obi) -> obi.get("dpid").equals(dpid)).collect(toList());

        if (match.size() == 1)
            return match.get(0);
        return null;
    }

    public Queue<Map<String, Object>> getMessages() {
        return messages;
    }
}
