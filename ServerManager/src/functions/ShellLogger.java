package functions;

import com.jcraft.jsch.*;

import javax.swing.*;

import server.Server;

public class ShellLogger{
	
  public static void run(Server server, TextAreaOutputStream areaOutputStream, String command){
    
    try{
      JSch jsch=new JSch();
      String host=null;
   
        host=server.getRootuser()+"@"+server.getDomain();
        
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);

      Session session=jsch.getSession(user, host, 22);

      String passwd = server.getRootpassword();
      session.setPassword(passwd);

      UserInfo ui = new MyUserInfo(){
        public void showMessage(String message){
          JOptionPane.showMessageDialog(null, message);
        }
        public boolean promptYesNo(String message){
          return true;
        }
      };

      session.setUserInfo(ui);
      session.connect(30000);   // making a connection with timeout.

      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);
      channel.setInputStream(System.in);
      channel.setOutputStream(areaOutputStream);
      channel.connect(3*1000);
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

  public static abstract class MyUserInfo
                          implements UserInfo, UIKeyboardInteractive{
    public String getPassword(){ return null; }
    public boolean promptYesNo(String str){ return false; }
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return false; }
    public boolean promptPassword(String message){ return false; }
    public void showMessage(String message){ }
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo){
      return null;
    }
  }
}
