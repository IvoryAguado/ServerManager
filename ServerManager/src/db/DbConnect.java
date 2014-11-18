package db;

import java.sql.*;

public interface DbConnect {

	/**
	 * 
	 * Defining SQL Server to connect in this case SQL
	 * 
	 * */

	public Connection connect()  throws ClassNotFoundException, SQLException;	
	public ResultSet getFromDB(String sql) throws SQLException;
	public int update(String sql) throws SQLException;	
	int insert(String sql) throws SQLException;	
	public int delete(String sql) throws SQLException;
	public void disconnect() throws SQLException;

	
}
