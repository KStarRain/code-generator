package com.kstarrain;

import java.util.*;

/**
 * 读取.properties配置文件的内容至Map中。
 * 
 * 
 */
class Config {
	private static Config instance;
	private Map<String, String> properties;

	private Config() {
		properties = read("properties/config");
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	/**
	 * 读取.properties配置文件的内容至Map中
	 * 
	 * @param propertiesFile
	 * @return
	 */
	private static Map<String, String> read(String propertiesFile) {

		//读取properties/config.properties文件
		ResourceBundle bundle = ResourceBundle.getBundle(propertiesFile);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enu = bundle.getKeys();
		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			String value = bundle.getString(key);
			map.put(key, value);
		}
		return map;
	}

	public String getProperty(String key) {
		return properties.get(key);
	}

	public String getDatabaseDriver() {
		return getProperty("driver");
	}

	public String getDatabaseURL() {
		return getProperty("url");
	}

	public Properties getDatabaseProperty() {
		Properties databaseProperties = new Properties();
		databaseProperties.put("user", getProperty("username"));
		databaseProperties.put("password", getProperty("password"));
		databaseProperties.put("remarksReporting",getProperty("remarksReporting"));
		return databaseProperties;
	}

}
