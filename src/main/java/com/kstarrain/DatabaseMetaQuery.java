package com.kstarrain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMetaQuery {

	public static List<TableMetadata> getTableMetas() throws Exception {
		System.out.println("begin connect database....");

		Config config = Config.getInstance();

		Class.forName(config.getDatabaseDriver()).newInstance(); //注册驱动
		Connection conn = DriverManager.getConnection(config.getDatabaseURL(),config.getDatabaseProperty()); //获取链接
		DatabaseMetaData database = conn.getMetaData(); //获取数据库的元数据。元数据包括关于数据库的表、受支持的SQL语法、存储过程、此连接功能等的信息

		List<TableMetadata> tables = new ArrayList<TableMetadata>();
		System.out.println("begin parse database....");

		String[] types = {"TABLE"};
		ResultSet tableResult = database.getTables(null, config.getProperty("schema"), config.getProperty("tables"), types); //获取数据库的表信息
		while (tableResult.next()) {

			String tableName = tableResult.getString("TABLE_NAME"); //表名
			String tableRemarks = tableResult.getString("REMARKS"); //表注释
            String entityClassName = config.getProperty("entityClassName"); //表对应实体类的名字
			TableMetadata table = new TableMetadata(tableName, tableRemarks, entityClassName);
			System.out.println("parsing table " + tableName);


			String primaryKey = "$";
			ResultSet primaryKeysResult = database.getPrimaryKeys(null, null, tableName); //获取表的主键
			
			int primaryKeysColumns = 0;
			while (primaryKeysResult.next()) {
                primaryKey = primaryKey + primaryKeysResult.getString("COLUMN_NAME") + "$";
                primaryKeysColumns ++;
			}
			System.out.println("table " + tableName + " primary key: " + primaryKey);


			/** 获取表的字段 */
			ResultSet columnResult = database.getColumns(null, null, tableName, null);
            table.setOneNumberPrimarykey(false);

			while (columnResult.next()) {
				ColomnMetadata colomn = table.addColumn(
                        columnResult.getString("COLUMN_NAME"),
                        columnResult.getString("TYPE_NAME"),
                        columnResult.getString("DECIMAL_DIGITS"),
                        columnResult.getString("REMARKS"),
                        columnResult.getString("COLUMN_SIZE"),
                        primaryKey.indexOf("$" + columnResult.getString("COLUMN_NAME") + "$") >= 0
				);
				
				if((primaryKeysColumns == 1) && colomn.isPrimaryKey() && colomn.getIsNumber()){
                    table.setOneNumberPrimarykey(true);
                    table.setPrimarykeycolumn(colomn.getFieldName());
				}
			}

			tables.add(table);
//			System.out.println(table.toString());
		}
		conn.close();
		System.out.println("end parse database....");
		return tables;
	}

}
