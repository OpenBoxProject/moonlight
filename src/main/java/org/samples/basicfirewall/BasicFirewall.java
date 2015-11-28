package org.samples.basicfirewall;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.blocks.Discard;
import org.moonlightcontroller.blocks.FromDevice;
import org.moonlightcontroller.blocks.HeaderClassifier;
import org.moonlightcontroller.blocks.ToDevice;
import org.moonlightcontroller.processing.Connector;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.types.TransportPort;

public class BasicFirewall extends BoxApplication{

	public BasicFirewall() {
		super("Firewall");
		this.setStatements(createStatements());
	}
	
	
	private List<IStatement> createStatements() {
		
		HeaderMatch h1 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, new TransportPort(22)).build();
		HeaderMatch h2 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.TCP_DST, TransportPort.ANY).build();
		
		ArrayList<IStatement> statements = new ArrayList<>();
		FromDevice from = new FromDevice.Builder().setDevice("eth0").setPromisc(true).setSniffer(true).build();
		ToDevice to = new ToDevice.Builder().setDevice("eth1").build();
		HeaderClassifier classify = new HeaderClassifier.Builder().addMatch(h1).addMatch(h2).build();
		Discard discard = new Discard.Builder().build();
		IStatement st = new Statement.Builder()
			.setLocation(new InstanceLocationSpecifier("ep1", 1000))
			.addBlock(from)
			.addBlock(to)
			.addBlock(classify)
			.addConnector(new Connector.Builder()
				.setSourceBlockId(from.getId())
				.setSourceBlockPort(from.getOutputPort())
				.setDestinationBlockId(classify.getId())
				.build())
			.addConnector(new Connector.Builder()
				.setSourceBlockId(classify.getId())
				.setSourceBlockPort(classify.getPort(h1))
				.setDestinationBlockId(to.getId())
				.build())
			.addConnector(new Connector.Builder()
				.setSourceBlockId(classify.getId())
				.setSourceBlockPort(classify.getPort(h2))
				.setDestinationBlockId(discard.getId())
				.build())
			.build();
		statements.add(st);
		return statements;
	}
}
