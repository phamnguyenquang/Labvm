package BackEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;

public class CommandExecutor {
	private ProcessBuilder pr;
	private Process p;
	private DefaultListModel<String> OSImageList = new DefaultListModel<String>();
	private boolean listed = false;
	String s = null;
	String s2 = null;

	public CommandExecutor() {

	}

	public String startCommand(String cmd) {
		String s1 = null;
		String[] cmd1 = { "/bin/bash", "-c", cmd };
		try {
			pr = new ProcessBuilder(cmd1);
			pr.redirectErrorStream(true);
			p = pr.start();
			// writer.write(pass);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			if (listed == false) {
				OSImageList.clear();
				while ((s = stdInput.readLine()) != null) {
//					System.out.println(s);
					OSImageList.addElement(s);

				}
			} else {
				while ((s2 = stdInput.readLine()) != null) {
//					System.out.println(s2);
					s = s2;
				}
			}
			while ((s1 = stdError.readLine()) != null) {
				System.out.println(s1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	public DefaultListModel<String> getOutput() {
		return OSImageList;
	}

	private void setListedState(boolean b) {
		listed = b;
	}

	public void listDir(String path) {
		setListedState(false);
		startCommand("sudo ls " + path);
		setListedState(true);
	}
}
