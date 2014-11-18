package server;

public class Server {

	private int Id;
	private String name;
	private String hostname;
	private String domain;
	private String ipaddress;
	private String rootuser;
	private String rootpassword;

	public Server(int Id, String name, String hostname, String domain,
			String ipaddress, String rootuser, String rootpassword) {
		super();
		this.Id = Id;
		this.name = name;
		this.hostname = hostname;
		this.domain = domain;
		this.ipaddress = ipaddress;
		this.rootuser = rootuser;
		this.rootpassword = rootpassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getRootuser() {
		return rootuser;
	}

	public void setRootuser(String rootuser) {
		this.rootuser = rootuser;
	}

	public String getRootpassword() {
		return rootpassword;
	}

	public void setRootpassword(String rootpassword) {
		this.rootpassword = rootpassword;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

}
