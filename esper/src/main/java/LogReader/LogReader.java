package LogReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Background.CommandExecutor;

public class LogReader {
	private String path;
	private File log;
	private BufferedReader br;
	private CommandExecutor linuxCommandExecutor = new CommandExecutor();
	private String lastLine = "";

	public LogReader(String filePath) {
		try {
			linuxCommandExecutor.startCommand("whoami");
			String user = linuxCommandExecutor.getResult();
			String alternateFile = "/home/"+user+"/auth.log";
			linuxCommandExecutor = new CommandExecutor();
			linuxCommandExecutor.startCommand("sudo cp " + filePath + " "+alternateFile);
			linuxCommandExecutor.startCommand("sudo chmod 777 "+alternateFile);
			this.path = alternateFile;
			log = new File(path);
			br = new BufferedReader(new FileReader(log));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshLog() {
		try {
			String st;
			while ((st = br.readLine()) != null) {
//				System.out.println(st);
				lastLine = st;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getLastLogLine()
	{
		refreshLog();
		return lastLine;
	}
}
