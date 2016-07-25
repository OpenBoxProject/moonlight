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
import org.moonlightcontroller.events.IAlertListener;
import org.moonlightcontroller.events.IHandleClient;
import org.moonlightcontroller.events.IInstanceUpListener;
import org.moonlightcontroller.events.InstanceAlertArgs;
import org.moonlightcontroller.events.InstanceUpArgs;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.Alert;
import org.moonlightcontroller.managers.models.messages.AlertMessage;
import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.ReadResponse;
import org.moonlightcontroller.processing.Connector;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.processing.ProcessingGraph;
import org.moonlightcontroller.topology.IApplicationTopology;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.moonlightcontroller.topology.Segment;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.types.TransportPort;

import com.google.common.collect.ImmutableList;

public class BasicFirewall extends BoxApplication{

	private final static Logger LOG = Logger.getLogger(BasicFirewall.class.getName()); 

	public BasicFirewall() {
		super("Firewall");
		this.setStatements(createStatements());
		this.setInstanceUpListener(new InstanceUpHandler());
		this.setAlertListener(new FirewallAlertListener());
	}

	@Override
	public void handleAppStart(IApplicationTopology top, IHandleClient handles) {
		LOG.info("Got App Start");
		new Thread(()-> {
			for (int i = 0 ; i < 10; i++){
				try {
					handles.readHandle(
							new InstanceLocationSpecifier(22), 
							"monkey",
							"buisness", new FirewallRequestSender());
				} catch (InstanceNotAvailableException e1) {
					LOG.warning("Unable to reach OBI");
				}
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private class FirewallAlertListener implements IAlertListener {
		
		@Override
		public void Handle(InstanceAlertArgs args) {
			Alert alert = args.getAlert();
			for (AlertMessage msg : alert.getMessages()) {
				LOG.info("got an alert from block:" + args.getBlock().getId() + "::" + msg.getMessage());	
			}
			
		}
	}
	
	private class FirewallRequestSender implements IRequestSender {

		@Override
		public void onSuccess(IMessage message) {
			if (message instanceof ReadResponse){
				ReadResponse rr = (ReadResponse)message;
				LOG.info("got a read response:" + rr.getBlockId() + "::" + rr.getReadHandle() + "::" + rr.getResult());
			}			
		}

		@Override
		public void onFailure(Error err) {
			LOG.info("got an error:" + err.getError_type() + "::" + err.getExtended_message());
		}
	}

	private List<IStatement> createStatements() {

		HeaderMatch h1 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, new TransportPort(22)).build();
		HeaderMatch h2 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, TransportPort.ANY).build();
		
		ArrayList<HeaderClassifierRule> rules = new ArrayList<HeaderClassifierRule>(Arrays.asList(
				new HeaderClassifierRule.Builder().setHeaderMatch(h1).setPriority(Priority.HIGH).setOrder(0).build(),
				new HeaderClassifierRule.Builder().setHeaderMatch(h2).setPriority(Priority.HIGH).setOrder(1).build()));

		FromDevice from = new FromDevice("BasicFirewall", "eth0", true, true);
		ToDevice to = new ToDevice("BasicFirewall", "eth1");
		HeaderClassifier classify = new HeaderClassifier("BasicFirewall", rules, Priority.HIGH, false);
		org.moonlightcontroller.blocks.Alert alert = 
				new org.moonlightcontroller.blocks.Alert("BasicFirewall.Alert", "Alert from firewall", 1, true, 100);
		Discard discard = new Discard("BasicFirewall");

		IProcessingGraph graph = new ProcessingGraph.Builder()
			.setBlocks(ImmutableList.of(from, to, classify, discard, alert))
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
			.setLocation(new Segment(220))
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