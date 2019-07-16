package com.kstarrain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableMetadata {
	private static final String TYPE_DECIMAL = "BigDecimal";
	private static final String TYPE_INTEGER = "Integer";
	private static final String TYPE_LONG = "Long";
	private static final String TYPE_STRING = "String";
	private static final String TYPE_BOOLEAN = "Boolean";
	private static final String TYPE_DATE = "Date";


	private String tableName;
	private String tableRemarks;
	private String tableNameUpperCase;
	private String entityClassName;
	private String entityPackageName;
	private String mapperClassName;
	private String mapperPackageName;

	private boolean oneNumberPrimarykey;
	private String primarykeycolumn;

	private List<ColomnMetadata> columnList = new ArrayList<ColomnMetadata>();
	private Set<String> typeImportList =  new HashSet<String>();

	public TableMetadata(String tableName, String tableRemarks, String entityClassName) {

		this.tableName = tableName; //表名
		this.tableRemarks = tableRemarks; //表备注
		this.tableNameUpperCase = "'"+ tableName.toUpperCase()+"'"; //将表名大写
		this.entityClassName = NameParser.getEntityClassName(tableName, entityClassName); //解析表名为entity类名
		this.entityPackageName = Config.getInstance().getProperty("targetPackage.entity");
		this.mapperClassName = this.entityClassName + "Mapper";
		this.mapperPackageName = Config.getInstance().getProperty("targetPackage.persistence");
	}

	public ColomnMetadata addColumn(String columnName, String columnType, String decimalDigits, String columnRemarks, String columnSize, boolean isPrimaryKey) {
		ColomnMetadata column = new ColomnMetadata();
		column.setColumnName(columnName);
		if (columnRemarks == null){columnRemarks = "";}
		column.setColumnRemarks(columnRemarks.trim());
		column.setColumnSize(columnSize);
		column.setColumnType(columnType);

		String javaType = parseJavaType(columnType, decimalDigits, columnSize);
		column.setJavaType(javaType);

		column.setIsNumber(isColumnNumber(columnType));
		column.setPrimaryKey(isPrimaryKey);
		columnList.add(column);
		return column;
	}

	private String parseJavaType(String columnType, String decimalDigits, String columnSize) {
		String ct;

		if ("VARCHAR".equals(columnType)) {
			ct = TYPE_STRING;
		} else if ("NUMBER".equals(columnType)||"INT".equals(columnType)||"DECIMAL".equals(columnType)||"DOUBLE".equals(columnType)) {
			if ("0".equals(decimalDigits)) {
				if(Integer.parseInt(columnSize)>10){
					ct = TYPE_LONG;
				}else{ 
					ct = TYPE_INTEGER;
				}
			} else {
				ct = TYPE_DECIMAL;
				typeImportList.add("java.math.BigDecimal");
			}
		} else if ("TINYINT".equals(columnType)) {
			ct = TYPE_INTEGER;
		} else if ("BIGINT".equals(columnType)) {
			ct = TYPE_LONG;
		} else if ("BIT".equals(columnType)) {
			ct = TYPE_BOOLEAN;
		} else if ("INTEGER".equals(columnType) || "SMALLINT".equals(columnType)) {
			ct = TYPE_INTEGER;
		} else if ("DATE".equals(columnType) || "TIMESTAMP".equals(columnType)|| "DATETIME".equals(columnType)) {
			ct = TYPE_DATE;
			typeImportList.add("java.util.Date");
		} else if ("BLOB".equals(columnType)) {
			ct = "Object";
		} else {
			ct = "String";
		}
		return ct;
	}
	
	
	private boolean isColumnNumber(String columnType){
		if ("NUMBER".equals(columnType)||"INT".equals(columnType)||"DECIMAL".equals(columnType)||"DOUBLE".equals(columnType)||"BIGINT".equals(columnType)||"INTEGER".equals(columnType)||"SMALLINT".equals(columnType)){
			return true;
		}
		return false;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableRemarks() {
		return tableRemarks;
	}

	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
	}

	public String getTableNameUpperCase() {
		return tableNameUpperCase;
	}

	public void setTableNameUpperCase(String tableNameUpperCase) {
		this.tableNameUpperCase = tableNameUpperCase;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}

	public String getMapperClassName() {
		return mapperClassName;
	}

	public void setMapperClassName(String mapperClassName) {
		this.mapperClassName = mapperClassName;
	}

	public String getMapperPackageName() {
		return mapperPackageName;
	}

	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}

	public boolean isOneNumberPrimarykey() {
		return oneNumberPrimarykey;
	}

	public void setOneNumberPrimarykey(boolean oneNumberPrimarykey) {
		this.oneNumberPrimarykey = oneNumberPrimarykey;
	}

	public String getPrimarykeycolumn() {
		return primarykeycolumn;
	}

	public void setPrimarykeycolumn(String primarykeycolumn) {
		this.primarykeycolumn = primarykeycolumn;
	}

	public List<ColomnMetadata> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColomnMetadata> columnList) {
		this.columnList = columnList;
	}

	public Set<String> getTypeImportList() {
		return typeImportList;
	}

	public void setTypeImportList(Set<String> typeImportList) {
		this.typeImportList = typeImportList;
	}
}
