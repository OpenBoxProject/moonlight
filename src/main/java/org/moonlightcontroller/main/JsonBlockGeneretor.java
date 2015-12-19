package org.moonlightcontroller.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBlockGeneretor {

	private static Map<String, String> blockClassMapping;

	public static void main(String[] args) {
		blockClassMapping = new HashMap<String, String>();
		blockClassMapping.put("T", "BlockClass.BLOCK_CLASS_TERMINAL");
		blockClassMapping.put("C", "BlockClass.BLOCK_CLASS_CLASSIFIER");
		blockClassMapping.put("M", "BlockClass.BLOCK_CLASS_MODIFIER");
		blockClassMapping.put("Sh", "BlockClass.BLOCK_CLASS_SHAPER");
		blockClassMapping.put("St", "BlockClass.BLOCK_CLASS_STATIC");
		
		Gson gson = new GsonBuilder().create();
		JsonFile json = gson.fromJson(readFile("blocks.json"), JsonFile.class);
		writeFiles(json);
	}

	private static void writeFiles(JsonFile json) {
		json.getClasses().forEach(currentClass -> writeFile(currentClass));
	}

	private static void writeFile(ClassObject currentClass) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("C:\\dev\\finalproject\\moonlightclone\\files\\" + 
					currentClass.getType() + ".java", "UTF-8");
			writer.println(getFileContent(currentClass));
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static String getFileContent(ClassObject currentClass) {
		StringBuilder sb = new StringBuilder();
		//write package
		sb.append("package org.moonlightcontroller.blocks;\n\n");

		//write imports
		sb.append("import org.moonlightcontroller.processing.ProcessingBlock;\n");
		sb.append("import java.util.Map;\n");
		sb.append("import org.moonlightcontroller.processing.BlockClass;\n");
		sb.append("\n");

		// write class name
		sb.append("public class " + currentClass.getType() + " extends ProcessingBlock {\n");

		// write fields
		for (ConfigurationObject config : currentClass.getConfiguration()) {
			sb.append("\tprivate " + config.getType() + " " + config.getName() + ";\n");
		}

		sb.append("\n");

		// write constructor
		sb.append("\tpublic " + currentClass.getType() + "(String id, ");
		for (ConfigurationObject config : currentClass.getConfiguration()) {
			sb.append(config.getType() + " " + config.getName() + ", ");
		}
		// replace last ',' with start method
		sb.delete(sb.length() - 2, sb.length());
		sb.append(") {\n");

		//write constructor body
		sb.append("\t\tsuper(id);\n");
		for (ConfigurationObject config : currentClass.getConfiguration()) {
			sb.append("\t\tthis." + config.getName() + " = " + config.getName() + ";\n");
		}

		//close constructor 
		sb.append("\t}\n");
		sb.append("\n");
		
		// create getters
		for (ConfigurationObject config : currentClass.getConfiguration()) {
			sb.append("\tpublic " + config.getType() + " get" + getName(config.getName()) + "() {\n");
			sb.append("\t\treturn " + config.getName() + ";\n");
			sb.append("\t}\n");
			sb.append("\n");
		}

		// create methods from ProcessingBlock inheritance
		List<String> methods = new ArrayList<>();
		methods.add("String getBlockType()");
		methods.add("String toShortString()");
		methods.add("ProcessingBlock clone()");

		for (String method : methods) {
			sb.append("\t@Override\n"
					+ "\tpublic "+ method + " {\n" +
					"\t\treturn null;\n"
					+ "\t}\n");
			sb.append("\n");
		}
		
		// build getBlockClass
		sb.append("\t@Override\n"
				+ "\tpublic BlockClass getBlockClass() {\n" +
				"\t\treturn " + blockClassMapping.get(currentClass.getBlockClass()) + ";\n"
				+ "\t}\n");
		sb.append("\n");
		
		//build putConfiguration method
		sb.append("\t@Override\n"
				+ "\tprotected void putConfiguration(Map<String, String> config) {\n");
		for (ConfigurationObject config : currentClass.getConfiguration()) {
			if (config.getType().equals("boolean")) {
				sb.append("\t\tconfig.put(\"" + config.getName() + "\", this." + config.getName() + "? \"true\" : \"false\");\n");
			} else if (config.getType().equals("int") || config.getType().equals("double")) {
				sb.append("\t\tconfig.put(\"" + config.getName() + "\", this." + config.getName() + "+ \"\");\n");
			}else {
				sb.append("\t\tconfig.put(\"" + config.getName() + "\", this." + config.getName() + ");\n");
			}
		}

		sb.append("\t}\n");

		// close class
		sb.append("}");

		return sb.toString();
	}

	private static String getName(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private static String readFile(String path) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(path);
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "UTF-8");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return writer.toString();
	}

}

class JsonFile {
	private List<ClassObject> classes;

	public JsonFile(List<ClassObject> classes) {
		this.classes = classes;
	}

	public List<ClassObject> getClasses() {
		return classes;
	}
	public void setClasses(List<ClassObject> classes) {
		this.classes = classes;
	}
}

class ClassObject {
	private String type;
	private List<ConfigurationObject> configuration;
	private List<ReadHandlersObject> read_handlers;
	private List <WriteHandlersObject> write_handlers;
	private String blockClass;

	public ClassObject(String type, List<ConfigurationObject> configuration, String blockClass) {
		this.type = type;
		this.configuration = configuration;
		this.blockClass = blockClass;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ConfigurationObject> getConfiguration() {
		return configuration;
	}
	public void setConfiguration(List<ConfigurationObject> configuration) {
		this.configuration = configuration;
	}

	public List<ReadHandlersObject> getRead_handlers() {
		return read_handlers;
	}

	public void setRead_handlers(List<ReadHandlersObject> read_handlers) {
		this.read_handlers = read_handlers;
	}

	public List <WriteHandlersObject> getWrite_handlers() {
		return write_handlers;
	}

	public void setWrite_handlers(List <WriteHandlersObject> write_handlers) {
		this.write_handlers = write_handlers;
	}

	public String getBlockClass() {
		return blockClass;
	}

	public void setBlockClass(String blockClass) {
		this.blockClass = blockClass;
	}
}

class ConfigurationObject {
	private String name; 
	private boolean required; 
	private String type; 
	private String description;

	public ConfigurationObject(String name, boolean required, String type, String description) {
		this.name = name;
		this.required = required;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

class ReadHandlersObject {
	private String name; 
	private String type; 
	private String description;

	public ReadHandlersObject(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

class WriteHandlersObject {
	private String name; 
	private String type; 
	private String description;

	public WriteHandlersObject(String name, String type, String description) {
		this.name = name;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}