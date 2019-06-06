package Development;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.xml.sax.XMLReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import XMLhandler.XMLReadWrite;

public class Network {
	private String IP = "10.8.0.2";
	private int port = 22;
	private String userName = "quang";
	private String passWord = "xxxx";
	private String workingDir = "/srv/tftp";

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private FTPClient ftp;
	private String connectionProtocol = "";
	private XMLReadWrite xml;

	public Network(String file) {
		xml = new XMLReadWrite(file);
		IP = xml.readValue("address");
		port = Integer.parseInt(xml.readValue("port"));
		userName = xml.readValue("user");
		System.out.println(IP);
		System.out.println(port);

	}

	public void makeSSHConnection(String server, String username, String pass) {
		try {
			JSch jsch = new JSch();
			userName = username;
			passWord = pass;
			IP = server;
			session = jsch.getSession(userName, IP, port);
			session.setPassword(passWord);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(workingDir);
			System.out.println("success");
			connectionProtocol = "SSH";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadFile(String path, String filename) {

		try {
			System.out.println(filename);
			if (connectionProtocol.equals("SSH")) {
				System.out.println(path);
				this.channelSftp.get(path, "/home/quang/" + filename);
			} else if (connectionProtocol.equals("FTP")) {
				System.out.println(path);
				FileOutputStream fos = new FileOutputStream("/home/quang/" + filename);
				ftp.retrieveFile(path, fos);
			}
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void uploadFile(String filename) {
		String pathFile = workingDir + filename;
		try {
			this.channelSftp.put("/source/remote/path/file.zip", "C:/target/local/path/file.zip");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFile() {
		try {
			Vector filelist = channelSftp.ls(workingDir);
			for (int i = 0; i < filelist.size(); i++) {
//				System.out.println(filelist.get(i).toString());
				LsEntry entry = (LsEntry) filelist.get(i);
				System.out.println(entry.getFilename());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void makeFTPConnection(String server, String username, String pass) {
		ftp = new FTPClient();
		IP = username;
		passWord = pass;
		IP = server;
		try {
			ftp.connect(IP, 21);
			ftp.login(userName, passWord);
//			ftp.enterLocalActiveMode();
			connectionProtocol = "FTP";
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFTPdir() {
		try {
			ftp.listFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFTPfile() {
		try {
			ftp.listFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void terminateConnection() {
		try {
			if (connectionProtocol.equals("FTP")) {
				ftp.logout();
				ftp.disconnect();
			} else if (connectionProtocol.equals("SSH")) {
				channelSftp.disconnect();
				session.disconnect();
				System.out.println("done");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String connectionType()
	{
		return connectionProtocol;
	}

}