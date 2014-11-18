package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import tools.SshTerminalEmulator;
import tools.TextEditor;
import account.AccountManager;

public class Administration extends JFrame {

	private static final long serialVersionUID = 6731907768364798311L;

	/**
	 * 
	 * Time to wait the message to disapear from notification
	 * 
	 * */

	private JPanel contentPane;

	static JLabel lblStatus = new JLabel("");

	private JTabbedPane scriptsTab;
	static JProgressBar progressBar = new JProgressBar();

	protected int z;

	/**
	 * Create the frame.
	 */
	public Administration() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				progressBar.setValue(0);
				lblStatus.setText(null);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				progressBar.setValue(0);
				lblStatus.setText(null);
			}
		});

		setPreferredSize(new Dimension(876, 600));

		setMinimumSize(new Dimension(876, 600));
		// setAlwaysOnTop(true);
		setName("mainFrame");
		setTitle("Administration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(51, 102, 255));
		menuBar.setBorder(null);
		setJMenuBar(menuBar);

		final JMenu mnAccount = new JMenu("Account");
		mnAccount.setFont(new Font("Calibri", Font.PLAIN, 14));
		mnAccount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

			}
		});
		mnAccount.setForeground(new Color(255, 255, 255));
		mnAccount.setBackground(Color.WHITE);
		menuBar.add(mnAccount);

		JMenuItem mntmChangePassword = new JMenuItem("Change Password...");
		mntmChangePassword.setFont(new Font("Calibri", Font.PLAIN, 14));
		mntmChangePassword.setBackground(Color.WHITE);
		mnAccount.add(mntmChangePassword);

		JMenuItem mntmLogout = new JMenuItem("LogOut");
		mntmLogout.setFont(new Font("Calibri", Font.PLAIN, 14));
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				
				String[] args=null;
				Login.main(args);
				
				for (java.awt.Window window : java.awt.Window.getWindows()) {
					window.dispose();
				}
				System.gc();
			}
		});
		mntmLogout.setBackground(Color.WHITE);
		mnAccount.add(mntmLogout);

		JMenu mnTools = new JMenu("Tools");
		mnTools.setFont(new Font("Calibri", Font.PLAIN, 14));
		mnTools.setBackground(Color.WHITE);
		mnTools.setForeground(new Color(255, 255, 255));
		menuBar.add(mnTools);

		JMenuItem mntmLaunchSshTerminal = new JMenuItem("Launch SSH Terminal");
		mntmLaunchSshTerminal.setFont(new Font("Calibri", Font.PLAIN, 14));
		mntmLaunchSshTerminal.setBackground(Color.WHITE);
		mntmLaunchSshTerminal.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F2, 0));
		mntmLaunchSshTerminal.setFocusTraversalPolicyProvider(true);
		mntmLaunchSshTerminal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SshTerminalEmulator sshTerminalEmulator = new SshTerminalEmulator();
				sshTerminalEmulator.startGUI();
			}
		});
		mnTools.add(mntmLaunchSshTerminal);

		JMenuItem mntmTextEditor = new JMenuItem("Text Editor");
		mntmTextEditor.setFont(new Font("Calibri", Font.PLAIN, 14));
		mntmTextEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TextEditor editor = new TextEditor();
				editor.setVisible(true);
			}
		});
		mntmTextEditor.setFocusTraversalPolicyProvider(true);
		mntmTextEditor.setBackground(Color.WHITE);
		mnTools.add(mntmTextEditor);

		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Calibri", Font.PLAIN, 14));
		mnHelp.setBackground(Color.WHITE);
		mnHelp.setForeground(Color.WHITE);
		menuBar.add(mnHelp);

		if (AccountManager.getLoggedUser().getUserPriviledge() >= 9) {
			JMenu mnAdminTools = new JMenu("Admin Tools");
			mnAdminTools.setForeground(Color.WHITE);
			mnAdminTools.setFont(new Font("Calibri", Font.PLAIN, 14));
			mnAdminTools.setBackground(Color.WHITE);
			menuBar.add(mnAdminTools);
		}
		contentPane = new JPanel();
		contentPane.setMinimumSize(new Dimension(620, 10));
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 10));
		panel.setMinimumSize(new Dimension(600, 10));
		panel.setBackground(new Color(30, 144, 255));
		panel.setAutoscrolls(true);

		setScriptsTab(null);
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager
			// .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(previousLF);
		} catch (IllegalAccessException | UnsupportedLookAndFeelException
				| InstantiationException | ClassNotFoundException e) {
		}

		// pack();
		setVisible(true);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);

		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblStatus.setText("Adding...");
				progressBar.setValue(32);
				
			}
		});
		btnNew.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNew.setForeground(Color.WHITE);
		btnNew.setBorderPainted(false);
		btnNew.setBackground(new Color(0, 102, 255));

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblStatus.setText("Editing...");
				progressBar.setValue(32);
			}
		});
		btnEdit.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setBorderPainted(false);
		btnEdit.setBackground(new Color(0, 102, 255));

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lblStatus.setText("Deleting...");
				progressBar.setValue(32);
				if (rootPaneCheckingEnabled) {
					lblStatus.setText("Deleted");
					progressBar.setValue(100);
				}else{
					lblStatus.setText("Not Deleted");
					progressBar.setValue(0);
				}
				
			}
		});
		btnDelete.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBorderPainted(false);
		btnDelete.setBackground(new Color(0, 102, 255));

		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
			
				try {
		
					if(!(AccountManager.initSession(AccountManager.getLoggedUser().getUsername(), AccountManager.getLoggedUser().getUsername()) !=null)){
						progressBar.setValue(100);
						lblStatus.setText("Refreshed!");
					}else{
					
						lblStatus.setText("Not Refreshed!");
						progressBar.setValue(0);
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					LookAndFeel previousLF = UIManager.getLookAndFeel();
					try {
						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
						new JOptionPane();
						JOptionPane
								.showMessageDialog(null,
										"Cannot Refresh Account :S, please try to log in again.");
					} catch (Exception k) {
					} finally {
						try {
							UIManager.setLookAndFeel(previousLF);
						} catch (UnsupportedLookAndFeelException j) {
						}
					}
				}
				
			}
		});
		btnRefresh.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnRefresh.setForeground(Color.WHITE);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setBackground(new Color(51, 102, 255));

		JLabel lblWelcome = new JLabel("Welcome "
				+ AccountManager.getLoggedUser().getUsername().toUpperCase()
				+ "");
		lblWelcome.setFont(new Font("Calibri", Font.PLAIN, 14));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNew, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(btnRefresh)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(163, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnRefresh)
						.addComponent(lblWelcome, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNew, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(8))
		);
		panel_2.setLayout(gl_panel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE))
					.addGap(12))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(12)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

		List<JButton> buttons = new ArrayList<JButton>();
		int i;
		for (i = 0; i < AccountManager.getLoggedUser().getServers().size(); i++) {

			JButton button = new JButton(""
					+ "<html>"
					+ "Registered ID: "
					+ AccountManager.getLoggedUser().getServers().get(i)
							.getId()
					+ "<br />"
					+ "<br />"
					+ "Name: "
					+ AccountManager.getLoggedUser().getServers().get(i)
							.getName()
					+ "<br />"
					+ "<br />"
					+ "Domain: "
					+ AccountManager.getLoggedUser().getServers().get(i)
							.getDomain()
					+ "<br />"
					+ "<br />"
					+ "IP: "
					+ AccountManager.getLoggedUser().getServers().get(i)
							.getIpaddress()
					+ "<br />"
					+ "</html>");

			button.setFont(new Font("Calibri", Font.PLAIN, 14));
			button.setBackground(Color.white);
			button.setSize(new Dimension(100, 100));
			setZ(i);

			button.addActionListener(new ActionListener() {
				int index = getZ();	

				public void actionPerformed(ActionEvent e) {

		
					try {
						ServerDashboard dashboard = new ServerDashboard(AccountManager.getLoggedUser().getServers().get(index));
					dashboard.setVisible(true);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});

			buttons.add(button);
			panel_1.add(buttons.get(i));

		}

		panel.setLayout(gl_panel);

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panel_3.setBackground(new Color(100, 149, 237));



		JLabel label_1 = new JLabel("");

		JLabel lblSmorenuburgds = new JLabel("");
		lblSmorenuburgds
				.setIcon(new ImageIcon(
						"C:\\Users\\Admin\\git\\servermanager\\ServerManager\\images\\SmorenburgLogo.png"));

		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3
				.setHorizontalGroup(gl_panel_3
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_3
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_3
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(label_1)
														.addGroup(
																gl_panel_3
																		.createSequentialGroup()
																		.addComponent(
																				progressBar,
																				GroupLayout.PREFERRED_SIZE,
																				202,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				lblStatus,
																				GroupLayout.PREFERRED_SIZE,
																				186,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				128,
																				Short.MAX_VALUE)
																		.addComponent(
																				lblSmorenuburgds,
																				GroupLayout.PREFERRED_SIZE,
																				170,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_panel_3
				.setVerticalGroup(gl_panel_3
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panel_3
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_panel_3
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(lblStatus)
														.addComponent(
																lblSmorenuburgds)
														.addComponent(label_1)
														.addComponent(
																progressBar,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		panel_3.setLayout(gl_panel_3);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] {
				contentPane, menuBar, mnAccount }));
	}

	public int getZ() {
		return z;
	}

	public void setZ(int in) {
		this.z = in;
	}

	public JTabbedPane getScriptsTab() {
		return scriptsTab;
	}

	public void setScriptsTab(JTabbedPane scriptsTab) {
		this.scriptsTab = scriptsTab;
	}

	public static void setProgressBar(JProgressBar jb) {
		progressBar=jb;
		
	}
}
