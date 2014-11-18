package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLException;

import javax.net.SocketFactory;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import account.AccountManager;
import db.Db;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private static final boolean autoLogin = false;
	
	
	
	
	public static boolean session = false;
	private JPanel contentPane;
	private JPasswordField pwdPassword;
	private JTextField txtUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Login frame = new Login();
					
					if (autoLogin) {
						frame.add(new JLabel("AUTOLOGIN ENABLED!"));
						if (AccountManager.initSession("admin",
								"1234") != null) {

							Administration administration = new Administration();
							administration.setVisible(true);
						}
					}else{
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
					}
				} catch (Exception e) {
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 144, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setKeyBindings();

		final JButton btnLogin = new JButton("LogIn");
		btnLogin.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnLogin.setBorderPainted(false);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					if (AccountManager.initSession(txtUsername.getText(),
							String.valueOf(pwdPassword.getPassword())) != null) {

						contentPane.setBackground(Color.green);
						Thread.sleep(3000);
						setVisible(false);
						Administration administration = new Administration();
						administration.setVisible(true);
					} else {
						contentPane.setBackground(Color.red);
					}
				} catch (ClassNotFoundException | SQLException e1) {
				} catch (InterruptedException e1) {
				} finally {
					try {
						Db.disconnect();
					} catch (SQLException e1) {
						System.exit(1);
					}
				}
			}
		});

		getForeground();
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setBounds(22, 130, 116, 30);
		contentPane.add(btnLogin);

		pwdPassword = new JPasswordField();
		pwdPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdPassword.setForeground(Color.DARK_GRAY);
		pwdPassword.setFont(new Font("Calibri", Font.PLAIN, 25));
		pwdPassword.setText("Passwords");
		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				contentPane.setBackground(new Color(30, 144, 255));
				if (!pwdPassword.getPassword().equals(null)) {
					pwdPassword.setText(null);
				}
			}
		});
		pwdPassword.setText("Password");
		pwdPassword.setBorder(null);
		pwdPassword.setBounds(47, 87, 198, 32);
		contentPane.add(pwdPassword);

		txtUsername = new JTextField();
		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsername.setForeground(Color.DARK_GRAY);
		txtUsername.setFont(new Font("Calibri", Font.PLAIN, 25));

		/** Setting Up PLaceHolder */
		txtUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				contentPane.setBackground(new Color(30, 144, 255));
				if (txtUsername.getText().equalsIgnoreCase("Username")) {
					getForeground();
					txtUsername.setText("");
					txtUsername.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsername.getText().equalsIgnoreCase("")) {
					getForeground();
					txtUsername.setForeground(Color.GRAY);
					txtUsername.setText("Username");
				} else {

				}
			}
		});
		txtUsername.setBorder(null);
		txtUsername.setBounds(47, 55, 198, 30);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		JButton btnLostPassword = new JButton("Lost Password?");
		btnLostPassword.setFont(new Font("Calibri", Font.PLAIN, 15));
		btnLostPassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LookAndFeel previousLF = UIManager.getLookAndFeel();
				try {
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					new JOptionPane();
					JOptionPane
							.showMessageDialog(null,
									"Please Contact to SmorenburgDS througth admin@smorenburg.diskstation.me");
				} catch (Exception e) {
				} finally {
					try {
						UIManager.setLookAndFeel(previousLF);
					} catch (UnsupportedLookAndFeelException e) {
					}
				}

			}
		});
		btnLostPassword.setBackground(SystemColor.controlHighlight);
		btnLostPassword.setBounds(144, 134, 140, 23);
		btnLostPassword.setBorderPainted(false);
		btnLostPassword.setOpaque(false);
		getForeground();
		btnLostPassword.setFocusPainted(false);
		;
		btnLostPassword.setForeground(new Color(255, 255, 255));
		contentPane.add(btnLostPassword);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 294, 44);
		contentPane.add(panel);

		JLabel lblServermanagerLogin = new JLabel("");
		lblServermanagerLogin
				.setIcon(new ImageIcon("..\\images\\SmorenburgLogo.png"));
		lblServermanagerLogin.setForeground(SystemColor.text);
		lblServermanagerLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addGap(67)
						.addComponent(lblServermanagerLogin,
								GroupLayout.PREFERRED_SIZE, 173,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(54, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addComponent(lblServermanagerLogin,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 44,
				Short.MAX_VALUE));
		panel.setLayout(gl_panel);

	}

	private void setKeyBindings() {
		contentPane.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("ENTER"), "clickENTER");
		contentPane.getRootPane().getActionMap()
				.put("clickENTER", new AbstractAction() {

					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (AccountManager.initSession(
									txtUsername.getText(),
									String.valueOf(pwdPassword.getPassword())) != null) {
								setVisible(false);
								Administration administration = new Administration();
								administration.setVisible(true);
							} else {
								contentPane.setBackground(Color.red);
							}
						} catch (ClassNotFoundException | SQLException e1) {
						}

						finally {
							try {
								Db.disconnect();
							} catch (SQLException e1) {
								System.exit(0);
							}
						}
					}
				});
	}
}
