package org.moonlightcontroller.aggregator.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.moonlightcontroller.aggregator.temp.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.Priority;

import com.google.common.collect.ImmutableList;

public class HeaderClassifier extends AbstractProcessingBlock implements IClassifierProcessingBlock {

	private String id;
	private List<? extends HeaderClassifierRule> rules;
	private Priority priority;
	
	public HeaderClassifier(String id) {
		this(id, new ArrayList<>(), Priority.MEDIUM);
	}
	
	public HeaderClassifier(String id, List<? extends HeaderClassifierRule> rules, Priority p) {
		this.id = id;
		this.rules = rules;
		this.priority = p;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public Priority getPriority() {
		return this.priority;
	}
	
	public List<HeaderClassifierRule> getRules() {
		return ImmutableList.copyOf(this.rules);
	}
	
	public int getNumberOfRules() {
		return this.rules.size();
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	public String getBlockType() {
		return "HeaderClassifier";
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new HeaderClassifier(id, this.getRules(), this.priority);
	}

	@Override
	public boolean canMergeWith(IClassifierProcessingBlock other) {
		return (other instanceof HeaderClassifier);
	}
	
	private static HeaderClassifierRuleWithSources aggregateRules(HeaderClassifierRule r1, HeaderClassifierRule r2, Priority p1, Priority p2, int src1, int src2, int order) throws MergeException {
		int pr1 = r1.getPriority().ordinal() * p1.ordinal() / 6;
		int pr2 = r2.getPriority().ordinal() * p2.ordinal() / 6;
		int newRulePrio = (pr1 > pr2 ? pr1 : pr2);
		
		HeaderMatch mergedMatch = r1.getMatch().mergeWith(r2.getMatch());
		
		Pair<Integer> sources = new Pair<Integer>(src1, src2);
		
		HeaderClassifierRuleWithSources newRule = new HeaderClassifierRuleWithSources(mergedMatch, Priority.of(newRulePrio), order, sources);
		
		return newRule;
	}
	
	@Override
	public IClassifierProcessingBlock mergeWith(IClassifierProcessingBlock other, IProcessingGraph containingGraph, List<Pair<Integer>> outPortSources) 
			throws MergeException {
		if (!(other instanceof HeaderClassifier))
			throw new MergeException("Cannot merge with the given type of classifier");
		HeaderClassifier o = (HeaderClassifier)other;
		
		// TODO: FIX THIS!!!
		// SECOND TRY: FULL CROSS PRODUCT

		// Create cross-product of rules table
		List<HeaderClassifierRuleWithSources> rules = new ArrayList<>();
		int order = 0;
		
		for (int i = 0; i < this.rules.size(); i++) {
			HeaderClassifierRule r1 = this.rules.get(i);
			for (int j = 0; j < o.rules.size(); j++) {
				HeaderClassifierRule r2 = o.rules.get(j);
				rules.add(aggregateRules(r1, r2, this.priority, o.priority, i, j, order));
				order++;
			}
		}
		Collections.sort(rules);
		//Collections.reverse(rules);

		Priority newP = (this.priority.compareTo(o.priority) > 0 ? this.priority : o.priority);
		
		IClassifierProcessingBlock merged = new HeaderClassifier("MERGED##" + this.id + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), rules, newP);
		
		for (HeaderClassifierRuleWithSources r : rules) {
			outPortSources.add(r.sources);
		}
				
		return merged;
	}
	
	/* OLD CODE
	public IClassifierProcessingBlock mergeWith(IClassifierProcessingBlock other, IProcessingGraph containingGraph, List<IConnector> newOutgoingConnectors) {
		if (!(other instanceof HeaderClassifier))
			throw new IllegalArgumentException("Cannot merge with the given type of classifier");
		HeaderClassifier o = (HeaderClassifier)other;
		
		// TODO: FIX THIS!!!
		// FIRST TRY: JUST APPEND RULES (WRONG RULES LOGIC, BUT OK FOR NOW)

		List<HeaderClassifierRule> rules = new ArrayList<>();
		rules.addAll(this.rules);
		rules.addAll(o.rules);

		Priority newP = (this.priority.compareTo(o.priority) < 0 ? this.priority : o.priority);
		
		IClassifierProcessingBlock merged = new HeaderClassifier("MERGED##" + this.id + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), rules, newP);
		
		containingGraph.getOutgoingConnectors(this)
			.forEach(c -> newOutgoingConnectors.add(new Connector(merged, c.getSourceOutputPort(), c.getDestBlock())));
		
		int secondBlockRulesOffset = this.getNumberOfRules();
		containingGraph.getOutgoingConnectors(o)
			.forEach(c -> newOutgoingConnectors.add(new Connector(merged, c.getSourceOutputPort() + secondBlockRulesOffset, c.getDestBlock())));		
				
		return merged;
	}
	*/
	
	public static class HeaderClassifierRule implements Comparable<HeaderClassifierRule> {
		private HeaderMatch match;
		private Priority priority;
		private int order;
		
		public HeaderClassifierRule(HeaderMatch match, Priority p, int order) {
			this.match = match;
			this.priority = p;
			this.order = order;
		}
		
		public HeaderMatch getMatch() {
			return this.match;
		}
		
		public Priority getPriority() {
			return this.priority;
		}

		@Override
		public int compareTo(HeaderClassifierRule o) {
			// Descending order - highest priority first / lowest order first
			if (this.priority != o.priority)
				return o.priority.compareTo(this.priority);
			else
				return this.order - o.order;
		}
	}
	
	private static class HeaderClassifierRuleWithSources extends HeaderClassifierRule {
		private Pair<Integer> sources;
		
		public HeaderClassifierRuleWithSources(HeaderMatch match, Priority p, int order, Pair<Integer> sources) {
			super(match, p, order);
			this.sources = sources;
		}
	}
}
