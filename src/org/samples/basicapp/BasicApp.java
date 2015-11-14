package org.samples.basicapp;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.blocks.FromDevice;
import org.moonlightcontroller.blocks.HeaderClassifier;
import org.moonlightcontroller.blocks.ToDevice;
import org.moonlightcontroller.processing.Connector;
import org.openboxprotocol.protocol.HeaderField;
import org.openboxprotocol.protocol.HeaderMatch;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.OpenBoxHeaderMatch;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.types.Port;

public class BasicApp extends BoxApplication {

	public BasicApp() {
		super("The most basic app in the world", Priority.HIGH);
		List<IStatement> statements = createStatements();
		this.setStatements(statements);
	}
	
	private List<IStatement> createStatements() {
		
		HeaderMatch h1 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.IN_PORT, Port.EMPTY_MASK).build();
		HeaderMatch h2 = new OpenBoxHeaderMatch.Builder().setExact(HeaderField.IN_PORT, Port.ANY).build();
		
		ArrayList<IStatement> statements = new ArrayList<>();
		FromDevice from = new FromDevice.Builder().setDevice("eth0").setPromisc(true).setSniffer(true).build();
		ToDevice to1 = new ToDevice.Builder().setDevice("eth1").build();
		ToDevice to2 = new ToDevice.Builder().setDevice("eth2").build();
		HeaderClassifier classify = new HeaderClassifier.Builder().addMatch(h1).addMatch(h2).build();
		IStatement st = new Statement.Builder()
			.addBlock(from)
			.addBlock(to1)
			.addBlock(classify)
			.addConnector(new Connector.Builder()
				.setSourceBlockId(from.getId())
				.setSourceBlockPort(from.getOutputPort())
				.setDestinationBlockId(classify.getId())
				.build())
			.addConnector(new Connector.Builder()
				.setSourceBlockId(classify.getId())
				.setSourceBlockPort(classify.getPort(h1))
				.setDestinationBlockId(to1.getId())
				.build())
			.addConnector(new Connector.Builder()
				.setSourceBlockId(classify.getId())
				.setSourceBlockPort(classify.getPort(h2))
				.setDestinationBlockId(to2.getId())
				.build())
			.build();
		statements.add(st);
		return statements;
	}
}
