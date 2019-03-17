package com.kstarrain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMetaQuery {

	public static List<TableMetadata> getTableMetas() throws Exception {
		System.out.println("begin connect database....");

		Config config = Config.getInstance();

		/** 注册驱动 */
		Class.forName(config.getDatabaseDriver()).newInstance();

		/** 获取链接 */
		Connection conn = DriverManager.getConnection(config.getDatabaseURL(),config.getDatabaseProperty());

		/** 获取数据库的元数据。元数据包括关于数据库的表、受支持的SQL语法、存储过程、此连接功能等的信息。 */
		DatabaseMetaData dm = conn.getMetaData();

		List<TableMetadata> tables = new ArrayList<TableMetadata>();
		System.out.println("begin parse database....");

		/** 获取数据库表信息 */
		String[] types = {"TABLE"};
		ResultSet rs = dm.getTables(null, config.getProperty("schema"),config.getProperty("tables"), types);
		while (rs.next()) {

			//获取表名
			String tbName = rs.getString("TABLE_NAME");
			//获取表描述(注释)
			String tbDesc = rs.getString("REMARKS");

			/** 构建表元数据 */
			TableMetadata tmd = new TableMetadata(tbName,tbDesc,config.getProperty("module"));

			System.out.println("parsing table " + tbName);
			String pk = "$";

			/** 获取表的主键 */
			ResultSet rsPK = dm.getPrimaryKeys(null, null, tbName);
			
			int pkcolumns = 0;
			while (rsPK.next()) {
				pk = pk + rsPK.getString("COLUMN_NAME") + "$";
				pkcolumns ++;
			}
			System.out.println("table " + tbName + " primary key: " + pk);


			/** 获取表的字段 */
			ResultSet rsCol = dm.getColumns(null, null, tbName, null);
			tmd.setOneNumberPrimarykey(false);

			while (rsCol.next()) {
				ColomnMetadata col = tmd.addCol(
						rsCol.getString("COLUMN_NAME"),
						rsCol.getString("TYPE_NAME"),
						rsCol.getString("DECIMAL_DIGITS"),
						rsCol.getString("REMARKS"),
						rsCol.getString("COLUMN_SIZE"),
						pk.indexOf("$" + rsCol.getString("COLUMN_NAME") + "$") >= 0
				);
				
				if((pkcolumns == 1) && col.isPkFlag() && col.getIsNumber()){
					tmd.setOneNumberPrimarykey(true);
					tmd.setPrimarykeycolumn(col.getFieldName());
				}

			}

			tables.add(tmd);
			System.out.println(tmd.toString());
		}
		conn.close();
		System.out.println("end parse database....");
		return tables;
	}

}
