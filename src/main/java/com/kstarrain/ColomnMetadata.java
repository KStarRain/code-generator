package com.kstarrain;

public class ColomnMetadata {
	private String columnName;
	private String columnRemarks;
	private String javaType;
	private String fieldName;
	private String columnSize;
	private String columnType;
	private String setterName;
	private String getterName;
	private Boolean isNumber;
	private boolean primaryKey;



	public String getSetterName() {
		return setterName;
	}

	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	public String getGetterName() {
		return getterName;
	}

	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
		String fieldName = NameParser.capitalize(columnName, false);
		String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
		this.setFieldName(fieldName);
		this.setSetterName(setterName);
		this.setGetterName(getterName);

	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColumnRemarks() {
		return columnRemarks;
	}

	public void setColumnRemarks(String columnRemarks) {
		this.columnRemarks = columnRemarks;
	}

	public String getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public Boolean getIsNumber() {
		return isNumber;
	}

	public void setIsNumber(Boolean isNumber) {
		this.isNumber = isNumber;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String toString() {
		return this.getColumnName() + "<" + this.getColumnRemarks() + ">";
	}
}
