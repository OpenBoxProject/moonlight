package org.openbox.dashboard;

import org.moonlightcontroller.processing.IProcessingBlock;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.openboxprotocol.protocol.IStatement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphsUtils {

    public static Map<String, Object> toPrettyGraph(IProcessingGraph graph) {

        Map<String, Object> prettyGraph = new HashMap<>();
        List<IProcessingBlock> blocks = graph.getBlocks();
        List<Map<String,String>> links = new ArrayList<>();
        graph.getConnectors()
                .stream().forEach((c)->{
            Map<String, String> link = new HashMap<>();
            link.put("sourceId", c.getSourceBlockId());
            link.put("sourcePort", String.valueOf(c.getSourceOutputPort()));
            link.put("destinationId", c.getDestinationBlockId());

            links.add(link);
        });

        prettyGraph.put("blocks", blocks);
        prettyGraph.put("connectors", links);
        return prettyGraph;

    }


    public static Map<String, Object> toPrettyStatement(IStatement statement) {

        Map<String, Object> prettyStatement = new HashMap<>();
        prettyStatement.put("location", statement.getLocation());
        prettyStatement.put("processingGraph", toPrettyGraph(statement.getProcessingGraph()));

        return prettyStatement;
    }

}
