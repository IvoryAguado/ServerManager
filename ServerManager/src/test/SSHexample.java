package test;

import com.jcraft.jsch.*;
import java.io.*;

public class SSHexample 
{
public static void Console(OutputStream outputStream)
{
    String user = "root";
    String password = "lo0ldcigigcsrq4kkvvb6o9nhe";
    String host = "h2261183.stratoserver.net";
    int port=22;

    String remoteFile="ty.txt";

    try
    {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        System.out.println("Establishing Connection...");
        session.connect();
        System.out.println("Connection established.");
        System.out.println("Crating SFTP Channel.");
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        System.out.println("SFTP Channel created.");
        InputStream out= null;
    out= sftpChannel.get(remoteFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(out));
        String line;
        while ((line = br.readLine()) != null) 
    {
            System.out.println(line);
        }
    br.close();
        sftpChannel.disconnect();
        session.disconnect();
    }
    catch(JSchException | SftpException | IOException e)
{
    System.out.println(e);
}
}
}