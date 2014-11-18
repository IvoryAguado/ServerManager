package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Main DB Class to consult for all the program
 * 
 * */

public class Db {


	public static ResultSet get(String sql) throws SQLException, ClassNotFoundException {
		//DB ( <typeDB> make <action> )
		MYSQLDB.make.connect();
		ResultSet query = MYSQLDB.make.getFromDB(sql);
		return query;
	}
	public static int delete(String sql) throws SQLException, ClassNotFoundException {
		//DB ( <typeDB> make <action> )
		MYSQLDB.make.connect();
		int rows = MYSQLDB.make.delete(sql);
		return rows;
	}
	public static int insert(String sql) throws ClassNotFoundException, SQLException {
		MYSQLDB.make.connect();
		int rows = MYSQLDB.make.insert(sql);
		return rows;
	}
	public static int update(String sql) throws ClassNotFoundException, SQLException {
		MYSQLDB.make.connect();
		int rows = MYSQLDB.make.update(sql);
		return rows;
	}

	public static void disconnect() throws SQLException{
		MYSQLDB.make.disconnect();		
	}

}
