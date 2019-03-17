package com.kstarrain;

public class NameParser {

	public static String getDomainName(String tableName, String module) {
//		int index = tableName.indexOf('_');
//		index = tableName.indexOf('_', index + 1);
//		String className = tableName.substring(index + 1);
		/** 切割表名例如t_student变为student */
		String className = tableName.substring(2);

		/** 以_符号 驼峰命名 */
		String domainName = capitalize(className, true);

		if(module!=null && module.trim().length()>0){
			domainName = domainName + module;
		}
		return domainName;
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
