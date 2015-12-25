package org.samples.basicfirewall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.blocks.Discard;
import org.moonlightcontroller.blocks.FromDevice;
import org.moonlightcontroller.blocks.HeaderClassifier;
import org.moonlightcontroller.blocks.HeaderClassifier.HeaderClassifierRule;
import org.moonlightcontroller.blocks.ToDevice;
import org.moonlightcontroller.events.IHandleClient;
import org.moonlightcontroller.events.IInstanceUpListener;
import org.moonlightcontroller.events.InstanceUpArgs;
import org.moonlightcontroller.processing.Connector;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.processing.ProcessingGraph;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.protocol.topology.IApplicationTopology;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.types.TransportPort;

import com.google.common.collect.ImmutableList;

public class BasicFirewall extends BoxApplication{

	private final static Logger LOG = Logger.getLogger(BasicFirewall.class.getName()); 

	public BasicFirewall() {
		super("Firewall");
		this.setStatements(createStatements());
		this.setInstanceUpListener(new InstanceUpHandler());
	}

	@Override
	public void handleAppStart(IApplicationTopology top, IHandleClient handles) {
		LOG.info("Got App Start");
	}

	private List<IStatement> createStatements() {

		HeaderMatch h1 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, new TransportPort(22)).build();
		HeaderMatch h2 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, TransportPort.ANY).build();
		
		ArrayList<HeaderClassifierRule> rules = new ArrayList<HeaderClassifierRule>(Arrays.asList(
				new HeaderClassifierRule.Builder().setHeaderMatch(h1).setPriority(Priority.HIGH).setOrder(0).build(),
				new HeaderClassifierRule.Builder().setHeaderMatch(h2).setPriority(Priority.HIGH).setOrder(1).build()));

		FromDevice from = new FromDevice("BasicFirewall", "eth0", true, true);
		ToDevice to = new ToDevice("BasicFirewall", "eth1");
		HeaderClassifier classify = new HeaderClassifier("BasicFirewall", rules, Priority.HIGH);
		Discard discard = new Discard("BasicFirewall");

		IProcessingGraph graph = new ProcessingGraph.Builder()
			.setBlocks(ImmutableList.of(from, to, classify, discard))
			.setConnectors(ImmutableList.of(
				new Connector.Builder()
					.setSourceBlock(from)
					.setSourceOutputPort(0)
					.setDestBlock(classify)
					.build(),
				new Connector.Builder()
					.setSourceBlock(classify)
					.setSourceOutputPort(0)
					.setDestBlock(to)
					.build(),
				new Connector.Builder()
					.setSourceBlock(classify)
					.setSourceOutputPort(1)
					.setDestBlock(discard)
					.build()))
			.build();
		
		IStatement st = new Statement.Builder()
			.setLocation(new InstanceLocationSpecifier("ep1", 1000))
			.setProcessingGraph(graph)
			.build();
		
		return Collections.singletonList(st);
	}

	private class InstanceUpHandler implements IInstanceUpListener {

		@Override
		public void Handle(InstanceUpArgs args) {
			LOG.info("Instance up for firewall: " + args.getInstance().toString());	
		}
	}
}