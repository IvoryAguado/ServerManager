package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class MYSQLDB {

	/**
	 * 
	 * MySQL DB
	 * 
	 * */
	static Connection con = null;
	
	
	public MYSQLDB() throws SQLException {
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
				con = DriverManager.getConnection("jdbc:mysql://192.168.1.100:3306/servermanager","servermanager", "**********");
				return con;
						
			} catch (ClassNotFoundException e) {
				new JOptionPane();
				JOptionPane.showMessageDialog(null, "Db Server is down or the configuration is incorrect.");
				
				System.exit(1);
				return con;
			}
		}

		@Override
		public void disconnect() throws SQLException {	
			try {
				con.close();
						
			} catch (Exception e) {
				LookAndFeel previousLF = UIManager.getLookAndFeel();
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					new JOptionPane();
					JOptionPane.showMessageDialog(null, "Db Server is down or the configuration is incorrect.");
					
				} catch (Exception ae) {
				} finally {
					try {
						UIManager.setLookAndFeel(previousLF);
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
						System.exit(1);

				}
				
			}
				
		}
	};
	
	
}
