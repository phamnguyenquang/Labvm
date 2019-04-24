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

	public SomeTest() {

	}

	public void makeConnection(String username, String pass) {
		try {
			JSch jsch = new JSch();
			SFTPUSER = username;
			SFTPPASS = pass;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void terminateSSHConnection() {
		channelSftp.disconnect();
		session.disconnect();
		System.out.println("done");
	}

	public void downloadFile(String filename) {
		String pathFile = SFTPWORKINGDIR + "/" + filename;
		try {
			System.out.println(pathFile);
			this.channelSftp.get(pathFile, "/home/quang/" + filename);
		} catch (SftpException e) {
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

	public void makeFTPConnection(String username, String pass) {
		ftp = new FTPClient();
		SFTPHOST = username;
		SFTPPASS = pass;
		try {
			ftp.connect(SFTPHOST, 69);
			ftp.login(SFTPUSER, SFTPPASS);
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

	public void terminateFTPConnection() {
		try {
			ftp.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}