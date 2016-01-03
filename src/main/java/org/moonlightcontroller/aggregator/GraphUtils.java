package org.moonlightcontroller.aggregator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.moonlightcontroller.blocks.Discard;
import org.moonlightcontroller.blocks.FromDevice;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.Connector;
import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.IProcessingBlock;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.processing.MutableProcessingGraph;
import org.moonlightcontroller.processing.ProcessingGraph;

import com.google.common.collect.ImmutableList;

class GraphUtils {
	
	public static IProcessingGraph normalizeToTree(IProcessingGraph graph) {
		MutableProcessingGraph mg = new MutableProcessingGraph(graph);
		
		boolean changed, first;
		
		do {
			changed = false;
			List<Supplier<Object>> actions = new ArrayList<>();
			for (IProcessingBlock b : mg.getBlocks()) {
				if (mg.getInDegree(b) > 1) {
					changed = true;
					
					List<IConnector> ins = mg.getConnectors().stream().filter(c -> c.getDestBlock().equals(b)).collect(Collectors.toList());
					
					first = true;
					
					for (IConnector c : ins) {
						if (first) {
							first = false;
							continue;
						}
						
						// For each incoming connector beyond the first one,
						// Should duplicate b
						// and change the connector to point to the duplicated block
						IProcessingBlock newB = b.clone();
						IConnector newC = new Connector.Builder()
												.setSourceBlock(c.getSourceBlock())
												.setSourceOutputPort(c.getSourceOutputPort())
												.setDestBlock(newB)
												.build();
						
						actions.add(() -> mg.addBlock(newB).replaceConnector(c, newC));
					}
				}
			}
			actions.forEach(s -> s.get());
		} while (changed);
		
		return mg;
	}
	
	/**
	 * This only works assuming root has only one successor!
	 * 
	 * @param g
	 * @param block
	 * @param root
	 * @param parent
	 * @param parentOutPort
	 * @param blocks
	 * @param connectors
	 */
	private static void cloneGraphRecursive(IProcessingGraph g, IProcessingBlock block, 
			boolean root, IProcessingBlock parent, int parentOutPort,
			List<IProcessingBlock> blocks, List<IConnector> connectors) {
		
		final IProcessingBlock clone = (root ? null : block.clone());
		
		if (!root) {
			// Clone block and add
			blocks.add(clone);
			
			// Add incoming connector
			connectors.add(new Connector.Builder()
								.setSourceBlock(parent)
								.setSourceOutputPort(parentOutPort)
								.setDestBlock(clone)
								.build());
		}
		
		// Recurse on children
		g.getOutgoingConnectors(block).stream()
			.forEach(c -> cloneGraphRecursive(g, c.getDestBlock(), false, 
					(root ? parent : clone), (root ? parentOutPort : c.getSourceOutputPort()), blocks, connectors));
	}
	
	public static IProcessingGraph concatGraphs(IProcessingGraph a, IProcessingGraph b) {
		Iterator<IProcessingBlock> iter = a.getBlocks().stream()
					 .filter(block -> (block.getBlockClass() == BlockClass.BLOCK_CLASS_TERMINAL && a.getOutDegree(block) == 0 && !(block instanceof Discard)))
					 .iterator();
		
		// Create new graph based on A
		MutableProcessingGraph pg = new MutableProcessingGraph(a);
		
		while (iter.hasNext()) {
			IProcessingBlock terminal = iter.next();
			List<IConnector> incoming = a.getIncomingConnectors(terminal);
			
			// Remove the terminal from the output graph
			pg.removeBlock(terminal);
			
			for (IConnector connector : incoming) {
				// Remove original connector
				IProcessingBlock src = connector.getSourceBlock();
				pg.removeConnector(connector);

				List<IProcessingBlock> newBlocks = new ArrayList<>();
				List<IConnector> newConnectors = new ArrayList<>();
				
				// Use cloneGraphRecursive to add a copy of b from src
				cloneGraphRecursive(b, b.getRoot(), true, src, connector.getSourceOutputPort(), 
						newBlocks, newConnectors);
				
				// Add new blocks and connectors to output graph
				pg.addBlocks(newBlocks).addConnectors(newConnectors);
			}
		}	
		return pg;
	}
}
