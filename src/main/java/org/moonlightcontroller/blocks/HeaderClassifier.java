package org.moonlightcontroller.blocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.aggregator.UUIDGenerator;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.Priority;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.google.common.collect.ImmutableList;

public class HeaderClassifier extends ProcessingBlock implements IClassifierProcessingBlock {

	private List<? extends HeaderClassifierRule> rules;
	private Priority priority;
	
	public HeaderClassifier(String id, List<? extends HeaderClassifierRule> rules, Priority priority) {
		super(id);
		this.priority = priority;
		this.rules = rules;
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
	protected ProcessingBlock spawn(String id) {
		return new HeaderClassifier(id, this.getRules(), this.priority);
	}

	@Override
	public boolean canMergeWith(IClassifierProcessingBlock other) {
		return (other instanceof HeaderClassifier);
	}


	@Override
	protected void putConfiguration(Map<String, Object> config) {
		config.put("priority", this.priority.toString());
		config.put("match", getRuleMaps());
	}
	
	private List<Map<String, String>> getRuleMaps() {
		List<Map<String, String>> l = new ArrayList<>();
		this.rules.forEach(r -> l.add(r.getRuleMap()));
		return l;
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
		
		// Remove duplicate rules
		Set<Integer> toRemove = new HashSet<>();
		for (int i = 0; i < rules.size(); i++) {
			for (int j = i + 1; j < rules.size(); j++) {
				if (rules.get(i).matchEquals(rules.get(j))) {
					// Same rule - remove rule j
					toRemove.add(j);
				}
			}
		}
		List<Integer> toRemoveLst = toRemove.stream().collect(Collectors.toList());
		Collections.sort(toRemoveLst);
		Collections.reverse(toRemoveLst);
		for (Integer i : toRemoveLst) {
			rules.remove(i.intValue());
		}

		Priority newP = (this.priority.compareTo(o.priority) > 0 ? this.priority : o.priority);
		
		IClassifierProcessingBlock merged = 
				new HeaderClassifier("MERGED##" + this.getId() + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), rules, newP);
		
		for (HeaderClassifierRuleWithSources r : rules) {
			outPortSources.add(r.sources);
		}
				
		return merged;
	}
	
	public static class HeaderClassifierRule implements Comparable<HeaderClassifierRule>, JsonSerializable {
		private HeaderMatch match;
		private Priority priority;
		private int order;
		
		private HeaderClassifierRule(){	
		}
		
		private HeaderClassifierRule(HeaderMatch match, Priority p, int order) {
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
		
		boolean matchEquals(HeaderClassifierRule other) {
			return this.match.equals(other.match);
		}
		
		@Override
		public String toString() {
			return String.format("[HeaderClassifierRule: priority: %s, order: %d, match: %s]", this.priority.name(), this.order, this.match.toString());
		}
		
		public Map<String, String> getRuleMap() {
			return this.match.getRuleMap();
		}
		
		public static class Builder {
			private HeaderClassifierRule rule;

			public Builder(){
				this.rule = new HeaderClassifierRule();
			}
			
			public Builder setHeaderMatch(HeaderMatch match){
				this.rule.match = match;
				return this;
			}
			
			public Builder setPriority(Priority p){
				this.rule.priority = p;
				return this;
			}
			
			public Builder setOrder(int o){
				this.rule.order = 0;
				return this;
			}
			
			public HeaderClassifierRule build(){
				return new HeaderClassifierRule(this.rule.match, this.rule.priority, this.rule.order);
			}
		}

		@Override
		public void serialize(JsonGenerator arg0, SerializerProvider arg1)
				throws IOException {
			this.match.serialize(arg0, arg1);
		}

		@Override
		public void serializeWithType(JsonGenerator arg0,
				SerializerProvider arg1, TypeSerializer arg2)
				throws IOException {
			this.match.serializeWithType(arg0, arg1, arg2);
		}
	}
	
	private static class HeaderClassifierRuleWithSources extends HeaderClassifierRule {
		private Pair<Integer> sources;
		
		public HeaderClassifierRuleWithSources(HeaderMatch match, Priority priority, int order, Pair<Integer> sources) {
			super(match, priority, order);
			this.sources = sources;
		}
	}
}
