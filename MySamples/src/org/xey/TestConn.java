package org.xey;

import org.xey.sys.DatabaseWork;

public class TestConn {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql = "insert into xey values(5)";
		DatabaseWork dbwork = new DatabaseWork();
		dbwork.executeMySQL(sql);
		dbwork.close();
	}

}
