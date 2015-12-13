package org.moonlightcontroller.aggregator.temp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.moonlightcontroller.aggregator.temp.HeaderClassifier.HeaderClassifierRule;
import org.moonlightcontroller.aggregator.temp.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.types.IPv4Address;
import org.openboxprotocol.types.TransportPort;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;


public class Aggregator {
	
	private static IProcessingGraph merge(IProcessingGraph a, IProcessingGraph b) {
		IProcessingGraph an = GraphUtils.normalizeToTree(a);
		IProcessingGraph bn = GraphUtils.normalizeToTree(b);
		
		System.out.println("Graph A normalized:");
		System.out.println(an);
		System.out.println("Graph B normalized:");
		System.out.println(bn);
		
		IProcessingGraph unified = GraphUtils.concatGraphs(an, bn);
		System.out.println("Unified uncompressed graph:");
		System.out.println(unified);
		
		MutableProcessingGraph unifiedMutable = new MutableProcessingGraph(unified);
		
		compress(unifiedMutable, unified.getRoot(), null, 0, null, 0);

		return unifiedMutable;
	}
	
	/**
	 * Returns a list of blocks that makes the path from 'from' until a non-static block is found, if such path exists, or null otherwise.
	 * Precondition: there are no branches in the requested path.
	 * 
	 * @param graph Graph to search path in
	 * @param from Start block
	 * @return list of blocks or null if no path exists
	 * @throws IllegalArgumentException when a branch is found
	 */
	private static List<IProcessingBlock> getStraightStaticsPath(IProcessingGraph graph, IProcessingBlock from) {
		IProcessingBlock current = from;
		List<IProcessingBlock> result = new ArrayList<>();
		
		while (current.getBlockClass() == BlockClass.BLOCK_CLASS_STATIC && graph.getOutDegree(current) == 1) {
			result.add(current);
			
			List<IProcessingBlock> successors = graph.getSuccessors(current);

			current = successors.get(0);
		}
		result.add(current);
		
		return result;
	}
	
	/*
	private static void cloneStraightPath(IProcessingGraph graph, IProcessingBlock from, IProcessingBlock to,
			List<IProcessingBlock> blocks, List<IConnector> connectors) {
		IProcessingBlock current = from;
		IProcessingBlock parent = null;
		int parentPort = 0;
		
		while (true) {
			if (parent != null) {
				connectors.add(new Connector.Builder()
						.setSourceBlock(parent)
						.setSourceOutputPort(parentPort)
						.setDestBlock(current)
						.build());
			}
			
			blocks.add(current.clone());
			
			if (current == to) {
				break;
			}
			
			List<IProcessingBlock> successors = graph.getSuccessors(current);
			if (successors.size() > 1) {
				throw new IllegalArgumentException("Path has branches");
			} else if (successors.size() == 0) {
				throw new IllegalArgumentException("No path found");
			}
			current = successors.get(0);
		}
	}
	*/
	
	private static boolean canMergeStatics(IStaticProcessingBlock v1, IStaticProcessingBlock v2) {
		// This should return true if the two blocks have exactly the same parameters
		return v1.canMergeWith(v2);
	}
	
	private static IProcessingBlock clonePath(IProcessingGraph graph,
			IProcessingBlock from, IProcessingBlock to, 
			IProcessingBlock parent, int parentPort,
			Collection<IProcessingBlock> blocks, Collection<IConnector> connectors,
			Set<IProcessingBlock> blocksToRemove, Set<IConnector> connectorsToRemove) {
		if (from == to)
			return parent;
		
		List<IConnector> outs = graph.getOutgoingConnectors(from);
		if (outs.size() == 0) {
			// No path exists
			return null;
		} else if (outs.size() > 1) {
			//throw new IllegalArgumentException("Unexpected branch encountered");
			return null;
		}
		
		IProcessingBlock newCurrent = from.clone();
		blocks.add(newCurrent);
		connectors.add(new Connector(parent, parentPort, newCurrent));
		blocksToRemove.add(from);
		connectorsToRemove.add(outs.get(0));
		
		return clonePath(graph, outs.get(0).getDestBlock(), to, newCurrent, outs.get(0).getSourceOutputPort(), blocks, connectors, blocksToRemove, connectorsToRemove);
	}
	
	private static void cloneSubTree(IProcessingGraph graph,
			IProcessingBlock current, IProcessingBlock parent, int parentPort,
			Collection<IProcessingBlock> blocks, Collection<IConnector> connectors,
			Set<IProcessingBlock> blocksToRemove, Set<IConnector> connectorsToRemove) {
		IProcessingBlock newCurrent = current.clone();
		IConnector newConnector = new Connector(parent, parentPort, newCurrent);
		blocksToRemove.add(current);
		blocks.add(newCurrent);
		connectors.add(newConnector);
		List<IConnector> outs = graph.getOutgoingConnectors(current);
		for (IConnector c: outs) {
			connectorsToRemove.add(c);
			cloneSubTree(graph, c.getDestBlock(), newCurrent, c.getSourceOutputPort(), blocks, connectors, blocksToRemove, connectorsToRemove);
		}
	}	
	
	// TODO: Complete here
	private static void compress(MutableProcessingGraph graph, IProcessingBlock current, 
			IProcessingBlock parent, int parentPort,
			IProcessingBlock start, int startOutPort) {
		
		System.out.println("[DEBUG current: " + current.toShortString() + 
						   ", parent: " + (parent == null ? "null" : parent.toShortString() + 
								   " (Port: " + parentPort + ")") + 
						   ", start: " + ((start == null) ? "null" : start.toShortString() + " (Port: " + startOutPort + ")"));
		
		// The problem is merging classifiers, modifiers and shapers (denoted from now - dynamic).
		// Also, statics cannot be reordered across such dynamic blocks.
		// So on every path, we find such blocks and try to work out the ones in between them.
		if (current.getBlockClass() == BlockClass.BLOCK_CLASS_CLASSIFIER ||
				current.getBlockClass() == BlockClass.BLOCK_CLASS_MODIFIER ||
				current.getBlockClass() == BlockClass.BLOCK_CLASS_SHAPER) {
			if (start == null) {
				// This is the first dynamic block we see (at this point of time)
				// We recurse on all output paths, marking this block as "start"
				graph.getOutgoingConnectors(current).stream()
					.forEach(c -> compress(graph, c.getDestBlock(),
							current, c.getSourceOutputPort(),
							current, c.getSourceOutputPort()));
				return;
			} else {
				// start is not null - we now have an end dynamic block
				IProcessingBlock end = current;
				List<IConnector> nextConnectors;
				Set<IProcessingBlock> blocksToRemove = new HashSet<>();
				Set<IConnector> connectorsToRemove = new HashSet<>();
				Set<IProcessingBlock> blocksToAdd = new HashSet<>();
				Set<IConnector> connectorsToAdd = new HashSet<>();
				List<Supplier<Object>> graphActions = new ArrayList<>();
				Set<IProcessingBlock> lastBeforeEnd = new HashSet<>();
				
				IProcessingBlock newStart;
				IProcessingBlock newCurrent = current;
				
				// If both blocks are classifiers of the same type we can merge them
				if (start.getBlockClass() == BlockClass.BLOCK_CLASS_CLASSIFIER &&
						end.getBlockClass() == BlockClass.BLOCK_CLASS_CLASSIFIER &&
						start instanceof IClassifierProcessingBlock &&
						end instanceof IClassifierProcessingBlock &&
						((IClassifierProcessingBlock)start).canMergeWith((IClassifierProcessingBlock)end)) {
					// Merge classifiers
					List<Pair<Integer>> outPortSources = new ArrayList<>();
					try {
						IProcessingBlock merged = ((IClassifierProcessingBlock)start).mergeWith((IClassifierProcessingBlock)end, graph, outPortSources);
						
						for (int i = 0; i < outPortSources.size(); i++) {
							// For each out port we have a pair (a, b). We should go over the path from 'start' out port a 
							// to 'end', and clone all blocks on the way except for 'end'. When reaching 'end', should take 
							// the sub-tree from 'end' out port b, clone it, and connect to 'end's parent.
							// On the way, we should mark all cloned blocks to remove.
							
							// Step 1: clone the path from 'start'[outPortSources[0]] to 'end' (excluding) and connect it from 'merged'[outPort]
							// (mark all cloned blocks to be removed)
							IProcessingBlock last = clonePath(graph, 
									graph.getOutgoingConnectors(start).get(outPortSources.get(i).get(0)).getDestBlock(), 
									end, 
									merged, 
									i, 
									blocksToAdd, connectorsToAdd, 
									blocksToRemove, connectorsToRemove);
							if (outPortSources.get(i).get(0) == startOutPort) {
								// Marking 'last' for later use when looking for statics to merge
								lastBeforeEnd.add(last);
							}
							
							// Step 2: clone the subtree going out of 'end'[outPortSources[1]] and connect it from the last block of the path cloned above
							// (mark all cloned blocks to be removed)
							cloneSubTree(graph, 
									graph.getOutgoingConnectors(end).get(outPortSources.get(i).get(1)).getDestBlock(), 
									last, 0, 
									blocksToAdd, connectorsToAdd, 
									blocksToRemove, connectorsToRemove);

						}
						// Replace 'start' with 'merged'
						blocksToAdd.add(merged);
						blocksToRemove.add(start);
						graph.getIncomingConnectors(start).forEach(c -> connectorsToAdd.add(new Connector(c.getSourceBlock(), c.getSourceOutputPort(), merged)));
						graph.getIncomingConnectors(start).forEach(c -> connectorsToRemove.add(c));
						
						blocksToRemove.add(end);
						
						List<IConnector> incoming = graph.getIncomingConnectors(end);
						// There should be exactly one connector in incoming
						if (incoming.size() != 1) {
							throw new IllegalStateException("This should not happen - incoming.size() == " + incoming.size());
						}
						IConnector inConnector = incoming.get(0);
						connectorsToRemove.add(inConnector);
						
						newStart = merged;
						newCurrent = merged;
						
						// Update graph
						blocksToRemove.forEach(b -> graph.removeBlock(b));
						connectorsToRemove.forEach(c -> graph.removeConnector(c));
						
						graph.getConnectors()
							.removeIf(c -> (!graph.getBlocks().contains(c.getSourceBlock()) || !graph.getBlocks().contains(c.getDestBlock()))); 

						System.out.println("Graph changed! New graph:\n" + graph);

					} catch (MergeException e) {
						// Cannot merge classifier
						newStart = start;
					}
				} else {
					// Cannot merge classifier
					newStart = start;
				}
				// For each connector going out from start (or merged), find statics to merge
				// until 'end'
				nextConnectors = graph.getOutgoingConnectors(newStart);

				for (IConnector next : nextConnectors) {
					// TODO: This must be done on the cloned blocks, can't do that on original graph
					// However, after the changes, 'end' does not exist in the graph anymore...
					// TODO: In fact this should be separated into two actions:
					// 1. Merge the classifiers, and mark the clones of the block that was before 'end'
					// 2. Search the paths from 'merged' to these marked clones for merging statics
					List<IProcessingBlock> path;
					path = getStraightStaticsPath(graph, next.getDestBlock());

					if (path == null) {
						continue;
					}

					for (int i = 0; i < path.size(); i++) {
						IProcessingBlock v1 = path.get(i);
						for (int j = i + 1; j < path.size() - 1; i++) {
							IProcessingBlock v2 = path.get(j);
							if (v1.getBlockType().equals(v2.getBlockType()) &&
									!blocksToRemove.contains(v1) &&
									!blocksToRemove.contains(v2) &&
									v1 instanceof IStaticProcessingBlock &&
									v2 instanceof IStaticProcessingBlock &&
									canMergeStatics((IStaticProcessingBlock)v1, (IStaticProcessingBlock)v2)) {
								// Found statics to merge - keep v1, remove v2
								blocksToRemove.add(v2);
								IConnector incoming = graph.getIncomingConnectors(v2).get(0);
								IConnector outgoing = graph.getOutgoingConnectors(v2).get(0);
								graphActions.add(() ->
									graph.addConnector(new Connector.Builder()
											.setSourceBlock(incoming.getSourceBlock())
											.setSourceOutputPort(incoming.getSourceOutputPort())
											.setDestBlock(outgoing.getDestBlock())
											.build()));
								connectorsToRemove.add(incoming);
								connectorsToRemove.add(outgoing);
							}
						}
					}
					
				}

				graphActions.forEach(supplier -> supplier.get());
				blocksToRemove.forEach(b -> graph.removeBlock(b));
				connectorsToRemove.forEach(c -> graph.removeConnector(c));
				
				graph.getConnectors()
					.removeIf(c -> (!graph.getBlocks().contains(c.getSourceBlock()) || !graph.getBlocks().contains(c.getDestBlock()))); 

				System.out.println("Graph changed! New graph:\n" + graph);
				
				// Recurse on successors
				final IProcessingBlock nstemp = newStart;
				final IProcessingBlock nctemp = newCurrent; 
				
				graph.getOutgoingConnectors(newCurrent).stream()
					.forEach(c -> compress(graph, c.getDestBlock(),
							nctemp, c.getSourceOutputPort(),
							(nctemp == nstemp) ? null : nstemp, c.getSourceOutputPort()));
			}
			
			
		} else {
			// Skip Terminals, Statics
			graph.getOutgoingConnectors(current).stream()
				.forEach(c -> compress(graph, c.getDestBlock(),
						current, c.getSourceOutputPort(),
						start, startOutPort));			
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		HeaderClassifierRule[] firewallRules = {
				new HeaderClassifierRule(
						new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, TransportPort.of(80)).build(),
						Priority.CRITICAL, 0),
				new HeaderClassifierRule(
						new OpenBoxHeaderMatch.Builder().setMasked(HeaderField.IPV4_SRC, IPv4Address.of("202.101.0.0"), IPv4Address.of("255.255.0.0")).build(),
						Priority.MEDIUM, 1),
				new HeaderClassifierRule(
						new OpenBoxHeaderMatch.Builder().build(),
						Priority.DEFAULT, 2)
		};
		
		IProcessingBlock[] firewallBlocks = {
				new FromDevice("FromDevice-Firewall"),
				new HeaderClassifier("HeaderClassifier-Firewall", ImmutableList.copyOf(firewallRules), Priority.CRITICAL),
				new Discard("Discard-Firewall"),
				new Alert("Alert-Firewall", "FIREWALL ALERT"),
				new ToDevice("ToDevice-Firewall")
		};
		IConnector[] firewallConnectors = {
				new Connector(firewallBlocks[0], 0, firewallBlocks[1]),
				new Connector(firewallBlocks[1], 0, firewallBlocks[2]),
				new Connector(firewallBlocks[1], 1, firewallBlocks[3]),
				new Connector(firewallBlocks[1], 2, firewallBlocks[4]),
				new Connector(firewallBlocks[3], 0, firewallBlocks[4])
		};
		
		HeaderClassifierRule[] lbRules = {
				new HeaderClassifierRule(
						new OpenBoxHeaderMatch.Builder().setExact(HeaderField.IPV4_DST, IPv4Address.of("10.0.0.1")).build(),
						Priority.VERY_HIGH, 0),
				new HeaderClassifierRule(
						new OpenBoxHeaderMatch.Builder().build(),
						Priority.DEFAULT, 1)
		};
		
		IProcessingBlock[] lbBlocks = {
				new FromDevice("FromDevice-LB"),
				new HeaderClassifier("HeaderClassifier-LB", ImmutableList.copyOf(lbRules), Priority.MEDIUM),
				new NetworkHeaderFieldsRewriter("NetworkHeaderFieldsRewriter-LB"),
				new ToDevice("ToDevice-LB")
		};
		IConnector[] lbConnectors = {
				new Connector(lbBlocks[0], 0, lbBlocks[1]),
				new Connector(lbBlocks[1], 0, lbBlocks[2]),
				new Connector(lbBlocks[1], 1, lbBlocks[3]),
				new Connector(lbBlocks[2], 0, lbBlocks[3])
		};
		
		IProcessingGraph a = new ProcessingGraph.Builder()
			.setBlocks(ImmutableList.copyOf(firewallBlocks))
			.setConnectors(ImmutableList.copyOf(firewallConnectors))
			.setRoot(firewallBlocks[0])
			.build();
		
		System.out.println("Input graph A:");
		System.out.println(a);
		
		IProcessingGraph b = new ProcessingGraph.Builder()
			.setBlocks(ImmutableList.copyOf(lbBlocks))
			.setConnectors(ImmutableList.copyOf(lbConnectors))
			.setRoot(lbBlocks[0])
			.build();
		
		System.out.println("Input graph B:");
		System.out.println(b);
		
		IProcessingGraph merged = merge(a, b);
		
		System.out.println("Result graph:");
		System.out.println(merged);
	}
}
