package account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.Server;
import db.Db;

public class AccountManager {
	
	
	private static User loggedUser;
	private static ArrayList<Server> servers;
	
	public static ResultSet initSession(String username, String passwd) throws ClassNotFoundException, SQLException {
		ResultSet query = Db.get("SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+passwd+"'");
		if (query.first()) {
			loggedUser = new User(Integer.parseInt(query.getString("ID")), query.getString("USERNAME"), query.getString("PASSWORD"), buildServerArray(query.getString("ID")), query.getString("PRIVILEDGE"));
			return query;
		}else{
			return null;
		}
	}
	
	private static ArrayList<Server> buildServerArray(String serversId) throws ClassNotFoundException, SQLException{
		ArrayList<Server> serversArray = new ArrayList<Server>();
		ResultSet rs = Db.get("SELECT * FROM SERVERS WHERE USERID='"+serversId+"'");
		
	//	int columnCount = rs.getMetaData().getColumnCount();
		while(rs.next()){	   
			serversArray.add(new Server(Integer.parseInt(rs.getString("ID")), rs.getString("NAME"), rs.getString("HOSTNAME"), rs.getString("DOMAIN"), rs.getString("IPADDRESS"), rs.getString("ROOTUSER"), rs.getString("ROOTPASSWORD")));
		}
		
		return serversArray;
	}

	public static User getLoggedUser() {
		return loggedUser;
	}

	public static ArrayList<Server> getServers() {
		return servers;
	}

	public static void setServers(ArrayList<Server> servers) {
		AccountManager.servers = servers;
	}

}
