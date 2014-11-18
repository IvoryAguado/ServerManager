package account;

import java.util.ArrayList;

import server.Server;

public class User {

	private int Id;
	private String username;
	private String password;
	private ArrayList<Server> servers = new ArrayList<Server>();
	
	private int userPriviledge;

	
	public User(int Id, String username, String password, ArrayList<Server> servers, String priviledge) {
		super();
		this.username = username;
		this.password = password;
		this.servers.addAll(servers);
		this.Id = Id;
		userPriviledge = Integer.parseInt(priviledge);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserPriviledge() {
		return userPriviledge;
	}

	public void setUserPriviledge(int userPriviledge) {
		this.userPriviledge = userPriviledge;
	}

	/**
	 * @return the servers
	 */
	public ArrayList <Server> getServers() {
		return servers;
	}
//
//	/**
//	 * @param servers the servers to set
//	 */
//	public void setServers(ArrayList <Server> servers) {
//		this.servers = servers;
//	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	
	
	
}
