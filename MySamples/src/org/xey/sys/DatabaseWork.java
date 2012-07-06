package org.xey.sys;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseWork {

	private Connection conn = null;
	private Statement stat = null;
	public ResultSet rs = null;
	private CallableStatement cstmt = null;

	public DatabaseWork() {
	}

	public static Connection getMysqlConnection() {
		Connection newcon = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			newcon = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/xey?user=xey&password=xey&autoReconnect=false");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newcon;
	}

	public boolean executeMySQL(String p_strSql) {
		boolean OperSuccess = false;
		int updateResult = 0;
		try {
			conn = getMysqlConnection();
			conn.setAutoCommit(false);
			if (conn == null) {
				return OperSuccess;
			} else {
				stat = conn.createStatement();
				if (p_strSql.trim().length() >= 6)
					if (p_strSql.trim().substring(0, 6).toLowerCase().equals("select")) {
						rs = stat.executeQuery(p_strSql);
						OperSuccess = true;
					} else if (p_strSql.trim().substring(0, 6).toLowerCase().equals("update")) {
						updateResult = stat.executeUpdate(p_strSql);
						if (updateResult == 1) {
							OperSuccess = true;
						}
					} else {
						stat.execute(p_strSql);
						OperSuccess = true;
					}
			}
		} catch (SQLException e) {
			OperSuccess = false;
		}
		return OperSuccess;
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println("Close Resultset Exception: " + e.getMessage());
			}
		}

		if (stat != null) {
			try {
				stat.close();
			} catch (Exception e) {
				System.out.println("Close Statement Exception: " + e.getMessage());
			}
		}

		if (cstmt != null) {
			try {
				cstmt.close();
			} catch (Exception e) {
				System.out.println("Close CallStatement Exception: " + e.getMessage());
			}
		}

		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					try {
						conn.commit();
						conn.setAutoCommit(true);
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
