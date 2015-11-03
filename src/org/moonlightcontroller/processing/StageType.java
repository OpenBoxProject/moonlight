package org.moonlightcontroller.processing;

public enum StageType {
	HeaderLookup,
	SessionAnalysis,
	ProtocolAnalysis,
	Decapsulation,
	Decryption,
	IngressHashing,
	FlowReconstruction,
	Decompression,
	Normalization,
	DPI,
	
	Restore,
	Compression,
	Encryption,
	Encapsulation,
	ModifyHeader,
	Cache,
	InstructionHandler
}
