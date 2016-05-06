package com.kyrin.MySqlConnection.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 抓包测试
 * @author kyrin
 *
 */
public class Test {

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");
	}
	
}
