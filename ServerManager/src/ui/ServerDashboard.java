package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Timer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import functions.ShellLogger;
import functions.TextAreaOutputStream;
import functions.ShellLogger.MyUserInfo;
import server.Server;
import server.ServerManager;

public class ServerDashboard extends JFrame {

	private static final long serialVersionUID = 2985016425777803071L;
	JLabel lblNewLabel;
	public Server serverM;
	JLabel soType;
	JTextArea textArea = new JTextArea();
	private ServerManager manager = new ServerManager();
	
	TextAreaOutputStream areaOutputStream = new TextAreaOutputStream(textArea, "");

	
	public static JLabel onlineStatus = new JLabel("");
	JLabel label_2 = new JLabel("");
	
	/**
	 * Create the frame.
	 * @param server 
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 * @throws InvocationTargetException 
	 */
	public ServerDashboard(Server server) throws UnknownHostException, IOException, InvocationTargetException, InterruptedException {
		serverM=server;
		
		//manager.checkOnline(server);
		
		setSize(new Dimension(700, 500));
		setPreferredSize(new Dimension(700, 500));
		// setAlwaysOnTop(true);
		setMinimumSize(new Dimension(700, 500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(30, 144, 255));
		
		JButton button = new JButton("Processes");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  ShellLogger.run(serverM,areaOutputStream,"ps aux");
			}
		});
		button.setForeground(new Color(0, 102, 255));
		button.setBorderPainted(false);
		button.setBackground(Color.WHITE);
		
		JButton button_1 = new JButton("Terminal");
		button_1.setForeground(new Color(0, 102, 255));
		button_1.setBorderPainted(false);
		button_1.setBackground(Color.WHITE);
		
		JButton button_2 = new JButton("File Manager");
		button_2.setForeground(new Color(0, 102, 255));
		button_2.setBorderPainted(false);
		button_2.setBackground(Color.WHITE);
		
		JButton button_3 = new JButton("System Log");
		button_3.setForeground(new Color(0, 102, 255));
		button_3.setBorderPainted(false);
		button_3.setBackground(Color.WHITE);
		
		JButton button_4 = new JButton("Reset Root Passwd");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					label_2.setText("Root Password: "+manager.generateNewRootPasswd(serverM).getRootpassword());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | SQLException
						| UnsupportedLookAndFeelException e) {}
			}
		});
		button_4.setForeground(new Color(0, 102, 204));
		button_4.setBorderPainted(false);
		button_4.setBorder(null);
		button_4.setBackground(Color.WHITE);
		
		JButton button_5 = new JButton("Reboot");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (manager.reboot(serverM)) {
					//dispose();
				}
			}
		});
		button_5.setForeground(new Color(0, 102, 255));
		button_5.setBorderPainted(false);
		button_5.setBackground(Color.WHITE);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(button_3, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(button_2, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_1)
						.addComponent(button_2))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_3))
					.addPreferredGap(ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager

		

				.getBorder("TitledBorder.border"), "Server Info",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBackground(Color.WHITE);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_5.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(184)
							.addComponent(onlineStatus))
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(50, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(onlineStatus)
						.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		
		panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel label = new JLabel("Name: "+server.getName());
		label.setFont(new Font("Calibri", Font.PLAIN, 13));
		panel_5.add(label);
		
		JLabel label_1 = new JLabel("Hostname: "+server.getHostname());
		label_1.setFont(new Font("Calibri", Font.PLAIN, 13));
		panel_5.add(label_1);
		
		label_2.setText("Root Passwd: "+server.getRootpassword());
		label_2.setFont(new Font("Calibri", Font.PLAIN, 13));
		label_2.setFocusable(true);
		panel_5.add(label_2);
		
		JLabel label_3 = new JLabel("IP: "+server.getIpaddress());
		label_3.setFont(new Font("Calibri", Font.PLAIN, 13));
		panel_5.add(label_3);
		
		JLabel label_4 = new JLabel("Domain: "+server.getDomain());
		label_4.setFont(new Font("Calibri", Font.PLAIN, 13));
		panel_5.add(label_4);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(30, 144, 255));
		
		JLabel label_7 = new JLabel("Server Console");
		label_7.setHorizontalTextPosition(SwingConstants.CENTER);
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Calibri", Font.PLAIN, 23));
		  
	
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addComponent(label_7, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
					.addContainerGap())
		);
		textArea.setIgnoreRepaint(true);
		textArea.setInheritsPopupMenu(true);
		textArea.setLineWrap(true);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(6)
					.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(30, 144, 255));
		
		JList<Server> list = new JList<Server>();
		
		JButton button_6 = new JButton("Throw Script");
		button_6.setForeground(new Color(0, 102, 255));
		button_6.setBorderPainted(false);
		button_6.setBackground(Color.WHITE);
		
		JButton button_7 = new JButton("Add new");
		button_7.setForeground(new Color(0, 102, 255));
		button_7.setBorderPainted(false);
		button_7.setBackground(Color.WHITE);
		
		JButton button_8 = new JButton("Edit");
		button_8.setForeground(new Color(0, 102, 255));
		button_8.setBorderPainted(false);
		button_8.setBackground(Color.WHITE);
		
		JButton button_9 = new JButton("Delete");
		button_9.setForeground(new Color(0, 102, 255));
		button_9.setBorderPainted(false);
		button_9.setBackground(Color.WHITE);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(list, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addGap(14)
							.addComponent(button_7, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_8, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_6)
						.addComponent(button_9)
						.addComponent(button_8)
						.addComponent(button_7))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(list, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_4, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 284, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		Runnable thread= new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						manager.checkOnline(serverM);
					} catch (IOException e) {
						ServerDashboard.onlineStatus.setIcon(new ImageIcon("C:\\Users\\Admin\\git\\servermanager\\ServerManager\\images\\offline.png"));
					}
				}
			}
		};
		new Thread(thread).start();
	}

	public void setInfoMessage(String message) {
		lblNewLabel.setText(message);
	}

	public Server getServer() {
		return serverM;
	}

	public void setServer(Server server) {
		serverM = server;
	}
}
