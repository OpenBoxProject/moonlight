package org.samples.basicfirewall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.blocks.Discard;
import org.moonlightcontroller.blocks.FromDevice;
import org.moonlightcontroller.blocks.HeaderClassifier;
import org.moonlightcontroller.blocks.ToDevice;
import org.moonlightcontroller.blocks.HeaderClassifier.HeaderClassifierRule;
import org.moonlightcontroller.events.IHandleClient;
import org.moonlightcontroller.events.IInstanceUpListener;
import org.moonlightcontroller.events.InstanceUpArgs;
import org.moonlightcontroller.processing.Connector;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.protocol.topology.IApplicationTopology;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.types.TransportPort;

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

		ArrayList<IStatement> statements = new ArrayList<>();

		FromDevice from = new FromDevice("BasicFirewall", 0, "eth0", true, true);
		ToDevice to = new ToDevice("BasicFirewall", "eth1");
		HeaderClassifier classify = new HeaderClassifier.Builder()
				.setRules(new ArrayList<HeaderClassifierRule>(Arrays.asList(
						new HeaderClassifierRule.Builder().setHeaderMatch(h1).setPriority(Priority.HIGH).setOrder(0).build(),
						new HeaderClassifierRule.Builder().setHeaderMatch(h2).setPriority(Priority.HIGH).setOrder(1).build())))
				.setPriority(Priority.HIGH)
				.build();
		Discard discard = new Discard("BasicFirewall");
		IStatement st = new Statement.Builder()
		.setLocation(new InstanceLocationSpecifier("ep1", 1000))
		.addBlock(from)
		.addBlock(to)
		.addBlock(classify)
		.addConnector(new Connector.Builder()
				.setSourceBlock(from)
				.setSourceOutputPort(from.getOutputPort())
				.setDestBlock(classify)
				.build())
		.addConnector(new Connector.Builder()
				.setSourceBlock(classify)
				.setSourceOutputPort(0)
				.setDestBlock(to)
				.build())
		.addConnector(new Connector.Builder()
				.setSourceBlock(classify)
				.setSourceOutputPort(1)
				.setDestBlock(discard)
				.build())
		.build();
		statements.add(st);
		return statements;
	}

	private class InstanceUpHandler implements IInstanceUpListener {

		@Override
		public void Handle(InstanceUpArgs args) {
			LOG.info("Instance up for firewall: " + args.getInstance().toString());	
		}
	}
}