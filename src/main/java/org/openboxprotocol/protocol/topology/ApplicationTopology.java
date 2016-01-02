package org.openboxprotocol.protocol.topology;


public class ApplicationTopology implements IApplicationTopology {

	private ITopologyManager topology;

	public ApplicationTopology(ITopologyManager top) {
		this.topology = top;
	}

	@Override
	public ILocationSpecifier reoslve(long id) {
		return this.topology.resolve(id);
	}
}
