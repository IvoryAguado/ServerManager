package server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.net.SocketFactory;
import javax.swing.ImageIcon;
import javax.swing.UnsupportedLookAndFeelException;

import ui.ServerDashboard;
import functions.ScriptLauncher;

public class ServerManager {

	ScriptLauncher launcher = new ScriptLauncher();

	SecureRandom generatedPasswd = new SecureRandom();
	static String newpasswd;

	public Server generateNewRootPasswd(Server server) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, UnsupportedLookAndFeelException {
		return ScriptLauncher.resetToGeneratedRootPasswd(server);
	}
	
	public boolean reboot(Server server){
		return ScriptLauncher.trowScript("reboot", server);
	}
	
	public void checkOnline(Server server) throws IOException {
		boolean open = true;
	    Socket socket = SocketFactory.getDefault().createSocket();
	    try {
	        socket.setSoTimeout(1000);
	        socket.connect(new InetSocketAddress(server.getDomain(),22));
	        socket.close();
	        if (open) {
				ServerDashboard.onlineStatus.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\servermanager\\ServerManager\\images\\online.png"));
			}else{
				ServerDashboard.onlineStatus.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\servermanager\\ServerManager\\images\\offline.png"));
			}
	    } catch (Exception e) {
	        open = false;
			ServerDashboard.onlineStatus.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\servermanager\\ServerManager\\images\\offline.png"));
	    }
	
	}
}
