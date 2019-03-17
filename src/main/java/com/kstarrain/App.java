package com.kstarrain;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class App {
	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		//获取根路径地址 /E:/work/kstarrain/kstarrain_code/generator/target/classes/
		String path = Class.class.getClass().getResource("/").getPath();
		System.out.println("Read Configuration from: " + path);

		//初始化config (读取properties/config.properties)
		Config config = Config.getInstance();

		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty(Velocity.RESOURCE_LOADER, "file");
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");

		Velocity.init(p);

		String fileSeparator = System.getProperty("file.separator");

		String outputPath = config.getProperty("outPath");
		if (outputPath == null || outputPath.trim().equals("")) {
			outputPath = path + "output/";
		}
		String projectPackage = config.getProperty("projectPackage");
		VelocityContext context = new VelocityContext();

		String[] templates = { "template/domain.vm","template/mapper.vm", "template/mapperInterface.vm"};
		String[] paths = { "persistence/entities","persistence/mappers", "persistence/mappers"};
		String[] fileNames = { ".java","Mapper.xml", "Mapper.java"};

		List<TableMetadata> tables = DatabaseMetaQuery.getTableMetas();
		for (TableMetadata table : tables) {

			context.put("meta", table);
			for (int i = 0; i < templates.length; i++) {
				String pathStr = paths[i];
				String outPath = outputPath + projectPackage.replace(".", fileSeparator) + "/" + pathStr + "/";

				File file = new File(outPath);
				if (!file.exists()) {file.mkdirs();}

				Template template = Velocity.getTemplate(templates[i]);
				template.setEncoding("utf-8");
				String fileName = outPath + table.getDomainClassName() + fileNames[i];
				writeFiles(template, context, fileName);
				System.out.println("write File: " + fileName);
			}
		}
		System.out.println("OK!");
	}

	private static void writeFiles(Template template, VelocityContext context,String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName, false);
		template.merge(context, fileWriter);
		fileWriter.close();
	}
	
	
}