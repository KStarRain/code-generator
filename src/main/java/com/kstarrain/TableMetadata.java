package com.kstarrain;

import java.util.ArrayList;
import java.util.List;

public class TableMetadata {
	private static final String TYPE_DECIMAL = "java.math.BigDecimal";
	private static final String TYPE_INTEGER = "Integer";
	private static final String TYPE_LONG = "Long";
	private static final String TYPE_STRING = "String";
	private static final String TYPE_BOOLEAN = "Boolean";

	private List<ColomnMetadata> cols = new ArrayList<ColomnMetadata>();

	private String tableDesc;
	private String tableName;
	private String tableNameUpperCase;
	private String domainClassName;
	private String domainPackageName;
	private String mapperInterfacePackageName;
	private String mapperInterfaceClassName;

	private boolean oneNumberPrimarykey;
	private String primarykeycolumn;
 

	public TableMetadata(String tableName, String tableDesc, String module) {
		this.tableDesc = tableDesc;
		this.tableName = tableName;
		//将表名大写
		this.tableNameUpperCase = "'"+ tableName.toUpperCase()+"'";
		//解析表名为domain类名
		this.domainClassName = NameParser.getDomainName(tableName, module);
		this.domainPackageName = Config.getInstance().getProperty("projectPackage")+ ".entities";
		this.mapperInterfaceClassName = this.domainClassName + "Mapper";
		this.mapperInterfacePackageName = Config.getInstance().getProperty("projectPackage")+ ".persistence";
	}

	public ColomnMetadata addCol(String colName, String colType, String pec, String desc, String length, boolean isPK) {
		ColomnMetadata col = new ColomnMetadata();
		col.setColName(colName);
		if (desc == null){desc = "";}
		col.setColDesc(desc.trim());
		col.setLength(length);

		String ct = parseDataType(colType, pec, length);
		col.setColType(ct);
		col.setColDBType(colType);
		col.setIsNumber(isColumnNumber(colType));
		col.setPkFlag(isPK);
		cols.add(col);
		return col;
	}

	private String parseDataType(String colType, String digits,String length) {
		String ct;

		if ("VARCHAR".equals(colType)) {
			ct = TYPE_STRING;
		} else if ("NUMBER".equals(colType)||"INT".equals(colType)||"DECIMAL".equals(colType)||"DOUBLE".equals(colType)) {
			if ("0".equals(digits)) {
				if(Integer.parseInt(length)>10){
					ct = TYPE_LONG;
				}else{ 
					ct = TYPE_INTEGER;
				}
			} else {
				ct = TYPE_DECIMAL;
			}
		}  else if ("TINYINT".equals(colType)) {
			ct = TYPE_INTEGER;
		}  else if ("BIGINT".equals(colType)) {
			ct = TYPE_LONG;
		} else if ("BIT".equals(colType)) {
			ct = TYPE_BOOLEAN;
		} else if ("INTEGER".equals(colType) || "SMALLINT".equals(colType)) {
			ct = TYPE_INTEGER;
		} else if ("DATE".equals(colType) || "TIMESTAMP".equals(colType)|| "DATETIME".equals(colType)) {
			ct = "java.util.Date";
		} else if ("BLOB".equals(colType)) {
			ct = "Object";

		} else {
			ct = "String";
		}
		return ct;
	}
	
	
	private boolean isColumnNumber(String colType){
		if ("NUMBER".equals(colType)||"INT".equals(colType)||"DECIMAL".equals(colType)||"DOUBLE".equals(colType)||"BIGINT".equals(colType)||"INTEGER".equals(colType)||"SMALLINT".equals(colType)){
			return true;
		}
		return false;
	}

	public String toString() {
		return "Dabase Table Name:" + this.tableName + "\n Table Columns:"
				+ this.cols.toString();

	}

	public List<ColomnMetadata> getCols() {
		return cols;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public String getTableName() {
		return tableName;
	}

	public String getDomainClassName() {
		return domainClassName;
	}

	public String getDomainPackageName() {
		return domainPackageName;
	}

	public String getMapperInterfacePackageName() {
		return mapperInterfacePackageName;
	}

	public String getMapperInterfaceClassName() {
		return mapperInterfaceClassName;
	}

	public String getTableNameUpperCase() {
		return tableNameUpperCase;
	}

	public void setTableNameUpperCase(String tableNameUpperCase) {
		this.tableNameUpperCase = tableNameUpperCase;
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
	
	

}
