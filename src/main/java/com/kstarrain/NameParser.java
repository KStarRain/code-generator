package com.kstarrain;

public class NameParser {

	public static String getEntityClassName(String tableName, String module) {

		String domainClassName;

		if(module != null && module.trim().length()>0){
			domainClassName =  module;
		}else {
			int index = tableName.indexOf("_");
			String className = tableName.substring(index + 1);
			/** 以_符号 驼峰命名 */
			domainClassName = capitalize(className, true);
		}
		return domainClassName;

	}

	public static String capitalize(String value, boolean firstUpper) {

		StringBuilder stringBuilder = new StringBuilder(value.length());

		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c != '_') {
				if (i == 0 && firstUpper) {
					stringBuilder.append(Character.toUpperCase(c));

				} else {
					stringBuilder.append(Character.toLowerCase(c));
				}
			} else {
				i++;
				c = value.charAt(i);
				stringBuilder.append(Character.toUpperCase(c));
			}
		}
		return stringBuilder.toString();
	}
}
