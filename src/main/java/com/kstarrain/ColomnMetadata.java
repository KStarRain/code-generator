package com.kstarrain;

public class ColomnMetadata {
	private String colName;
	private String colDesc;
	private String colType;
	private String fieldName;
	private String length;
	private String colDBType;
	private String seOperName;
	private String geOperName;
	private Boolean isNumber;

	private boolean pkFlag;

	public boolean isPkFlag() {
		return pkFlag;
	}

	public void setPkFlag(boolean pkFlag) {
		this.pkFlag = pkFlag;
	}

	public String getGeOperName() {
		return geOperName;
	}

	public void setGeOperName(String geOperName) {
		this.geOperName = geOperName;
	}

	public String getSeOperName() {
		return seOperName;
	}

	public void setSeOperName(String seOperName) {
		this.seOperName = seOperName;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;

		String fn = NameParser.capitalize(colName, false);
		String fnS = "set" + Character.toUpperCase(fn.charAt(0))
				+ fn.substring(1);
		String fnG = "get" + Character.toUpperCase(fn.charAt(0))
				+ fn.substring(1);
		this.setFieldName(fn);
		this.setSeOperName(fnS);
		this.setGeOperName(fnG);

	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {

		this.colType = colType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getColDesc() {
		return colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getColDBType() {
		return colDBType;
	}

	public void setColDBType(String colDBType) {
		this.colDBType = colDBType;
	}
	

	public Boolean getIsNumber() {
		return isNumber;
	}

	public void setIsNumber(Boolean isNumber) {
		this.isNumber = isNumber;
	}

	public String toString() {
		return this.getColName() + "<" + this.getColDesc() + ">";
	}
}
