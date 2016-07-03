package org.moonlightcontroller.blocks;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.moonlightcontroller.processing.IProcessingBlock;
import org.moonlightcontroller.processing.ProcessingBlock;

/**
 * A class which should be overridden by custom block implementations
 */
public abstract class CustomBlock extends ProcessingBlock implements IProcessingBlock {

	private String binaryPath;
	private Map<ObiType, Object> translations;
	private String module_name;
	
	public CustomBlock(String id, String binpath, String module_name, Map<ObiType, Object> translations) {
		super(id);
		this.binaryPath = binpath;
		this.translations = translations;
		this.module_name = module_name;
	}
	
	/***
	 * Get the corresponding translation object
	 * @param t the type of the OBI
	 * @return the translation object for the requested OBI type
	 */
	public Object getTranslationObject(ObiType t){
		return this.translations.get(t);
	}

	public Map<ObiType, Object> getTranslations(){
		return this.translations;
	}

	/**
	 * Get the module name
	 * @return the module name
	 */
	public String getModuleName(){
		return this.module_name;
	}

	/**
	 * Gets the module content as base 64 string
	 * @return the module content as base64 string
	 * @throws IOException 
	 */
	public String getModuleContent() throws IOException {
		File file = new File(this.binaryPath);
		byte[] data = FileUtils.readFileToByteArray(file);
		String encoded = Base64.getEncoder().encodeToString(data);
		return encoded;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {	
	}
}
