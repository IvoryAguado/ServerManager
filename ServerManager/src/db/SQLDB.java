package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class SQLDB {

	/**
	 * 
	 * SQL DB ADDRESS
	 * 
	 * */
	static final String _URL_JDBC = "jdbc:oracle:thin:HR/1@localhost:1521:XE";
	static Connection con = null;
	
	public SQLDB() {
		super();
	}

	/**
	 * 
	 * Making static for anyone can use it.
	 * 
	 * */
	static DbConnect make = new DbConnect() {
				
		/**
		 * 
		 * Send Data to modify
		 * - Create Statement
		 * - return number of rows modified
		 * - Close query statement
		 * 
		 * @throws SQLException 
		 * 
		 * */
		@Override
		public int update(String sql) throws SQLException {
			int nfilas;
			Statement stm = con.createStatement();
			nfilas = stm.executeUpdate(sql);
			stm.close();
			return nfilas;		
		}
		
		/**
		 * 
		 * Get data from SQL query
		 * @throws SQLException 
		 * 
		 * */		
		@Override
		public ResultSet getFromDB(String sql) throws SQLException {
			ResultSet rs = null;
			Statement stm = con.createStatement();
			rs = stm.executeQuery(sql);
			return rs;
		}
		
		@Override
		public int insert(String sql) throws SQLException {
			int nfilas;
			Statement stm = con.createStatement();
			nfilas = stm.executeUpdate(sql);
			stm.close();
			return nfilas;
		}
		
		@Override
		public int delete(String sql) throws SQLException {
			int nfilas;
			Statement stm = con.createStatement();
			nfilas = stm.executeUpdate(sql);			
			stm.close();
			return nfilas;		
		}
		
		/**
		 * 
		 * Here we setup the driver to connect to an SQL DB.
		 * @throws SQLException 
		 * @throws ClassNotFoundException
		 * 
		 * */
		@Override
		public java.sql.Connection connect() throws SQLException {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(_URL_JDBC);
				return con;
				
			} catch (ClassNotFoundException e) {
				System.err.println("Driver de conexión no disponible");
				e.printStackTrace();
				//Si no encontramos el driver, debemos terminar el programa
				//e indicamos mediante el código 1 que la terminación
				//ha sido por un error.
				System.exit(1);
				return null;
			}
		}

		@Override
		public void disconnect() throws SQLException {
			con.close();		
		}
	};
	
	
}
