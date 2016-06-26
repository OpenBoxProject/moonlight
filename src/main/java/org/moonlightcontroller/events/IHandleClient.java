package org.moonlightcontroller.events;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;

/**
 * Interface which passed back the OpenBox Applications
 * It is a subset of the Southbound API capabilities to allow application
 * to query and set handles on the OBI
 */
public interface IHandleClient {
	// TODO: are handles represented as strings?
	/**
	 * Read a handle
	 * @param loc the location specifier of the OBI
	 * @param handle the name of the handle
	 * @param blockId the block ID
	 * @param sender the sender interface
	 * @throws InstanceNotAvailableException
	 */
	void readHandle(ILocationSpecifier loc, String handle, String blockId, IRequestSender sender) throws InstanceNotAvailableException;
	
	/**
	 * Write to a handle
	 * @param loc the location specifier of the OBI
	 * @param blockId the block ID
	 * @param handle the name of the handle
	 * @param value the value to set
	 * @param sender the sender interface
	 * @throws InstanceNotAvailableException
	 */
	void writeHandle(ILocationSpecifier loc, String blockId, String handle, String value,
			IRequestSender sender) throws InstanceNotAvailableException;
}
