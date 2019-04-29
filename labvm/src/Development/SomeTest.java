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

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SomeTest {
	private String SFTPHOST = "10.8.0.2";
	private int SFTPPORT = 22;
	private String SFTPUSER = "quang";
	private String SFTPPASS = "xxxx";
	private String SFTPWORKINGDIR = "/srv/tftp";

	private Session session = null;
	private Channel channel = null;
	private ChannelSftp channelSftp = null;
	private FTPClient ftp;
	private String connectionType = "";

	public SomeTest() {

	}

	public void makeSSHConnection(String server, String username, String pass) {
		try {
			JSch jsch = new JSch();
			SFTPUSER = username;
			SFTPPASS = pass;
			SFTPHOST = server;
			session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session.setPassword(SFTPPASS);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			System.out.println("success");
			connectionType = "SSH";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadFile(String path, String filename) {

		try {
			System.out.println(filename);
			if (connectionType.equals("SSH")) {
				System.out.println(path);
				this.channelSftp.get(path, "/home/quang/" + filename);
			} else if (connectionType.equals("FTP")) {
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
		String pathFile = SFTPWORKINGDIR + filename;
		try {
			this.channelSftp.put("/source/remote/path/file.zip", "C:/target/local/path/file.zip");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFile() {
		try {
			Vector filelist = channelSftp.ls(SFTPWORKINGDIR);
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
		SFTPHOST = username;
		SFTPPASS = pass;
		SFTPHOST = server;
		try {
			ftp.connect(SFTPHOST, 21);
			ftp.login(SFTPUSER, SFTPPASS);
//			ftp.enterLocalActiveMode();
			connectionType = "FTP";
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
			if (connectionType.equals("FTP")) {
				ftp.logout();
				ftp.disconnect();
			} else if (connectionType.equals("SSH")) {
				channelSftp.disconnect();
				session.disconnect();
				System.out.println("done");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}