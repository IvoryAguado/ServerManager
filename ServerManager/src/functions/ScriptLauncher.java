
package functions;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import db.Db;
import server.Server;
import net.neoremind.sshxcute.core.*;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import net.neoremind.sshxcute.task.CustomTask;
import net.neoremind.sshxcute.task.impl.ExecCommand;

public class ScriptLauncher {

	static int i=0;
	static ConnBean cb = null;

	static SecureRandom generatedPasswd = new SecureRandom();
	private static String newpasswd;


	public static boolean trowScript(String commandScript, Server server) {
		SSHExec ssh = null;
		Result res = null;
		CustomTask command = null;
		ConnBean cb = null;
		//System.out.println(commandScript + "\n " + server.getRootpassword());
		
		try {
			ssh = null;
			cb = new ConnBean(server.getDomain(), server.getRootuser(), server.getRootpassword());
			ssh = SSHExec.getInstance(cb);
			command = new ExecCommand(commandScript);
			ssh.connect();
			res = ssh.exec(command);
			cb.setPassword(server.getRootpassword());
		} catch (TaskExecFailException e) {
		}

		finally {
			System.out.println(server.getRootpassword());
			System.out.println(server.getRootpassword());
			ssh.disconnect();
			System.gc();
		}
		return res.isSuccess;
	}

	public static Server resetToGeneratedRootPasswd(Server server)
			throws ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		newpasswd = generateNewPasswd();
		
		if (ScriptLauncher.changePasswOnServer(server)) {

			if (registerNewPasswdToDb(newpasswd, server.getId())) {
				server.setRootpassword(newpasswd);
				// System.exit(0);
			} else {
				new JOptionPane();
				JOptionPane.showMessageDialog(null,
						"Password not changed in the DB.");
			}
			// TODO need to setup a refresh of the sertver details as password
			// and that stuff ffor future use of server ssh because is changes
			// and not recharged
		} else {
			LookAndFeel previousLF = UIManager.getLookAndFeel();
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new JOptionPane();
			JOptionPane.showMessageDialog(null, "Password not Changed !! =(");
			UIManager.setLookAndFeel(previousLF);
		}
		return server;
	}
	
	private static boolean registerNewPasswdToDb(String rootpasswd, int ID)
			throws ClassNotFoundException, SQLException {
		// TODO change to set up onluy the good server
		if (Db.update("UPDATE SERVERS SET ROOTPASSWORD = '" + rootpasswd
				+ "' WHERE ID='" + ID + "';") > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	private static boolean changePasswOnServer(Server server) {
		SSHExec ssh = null;
		Result res = null;
		CustomTask command = null;
		ConnBean cb = null;
		//System.out.println(commandScript + "\n " + server.getRootpassword());
		newpasswd = generateNewPasswd();
		try {
			ssh = null;
			cb = new ConnBean(server.getDomain(), server.getRootuser(), server.getRootpassword());
			ssh = SSHExec.getInstance(cb);
			command = new ExecCommand("echo -e '"+newpasswd+"\n"+newpasswd+"' | passwd "+server.getRootuser()+"");
			ssh.connect();
			res = ssh.exec(command);
			cb.setPassword(server.getRootpassword());
		} catch (TaskExecFailException e) {
		}

		finally {
			System.out.println(server.getRootpassword());
			System.out.println(server.getRootpassword());
			ssh.disconnect();
			System.gc();
		}
		return res.isSuccess;

	}
	
	private static String generateNewPasswd() {
		return new BigInteger(90, generatedPasswd).toString(32);
	}
}
