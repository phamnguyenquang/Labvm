package BackEnd_Misc;

import XMLhandler.XMLReadWrite;

public class networkHandler {
	private CommandExecutor cmd;
	private XMLReadWrite xml;
	private String networkFile;

	public networkHandler() {
		cmd = new CommandExecutor();
		xml = new XMLReadWrite();
	}
	
	public networkHandler(String filepath) {
		cmd = new CommandExecutor();
		setFilePath(filepath);
	}
	
	public void setFilePath(String filepath)
	{
		networkFile = filepath;
		xml = new XMLReadWrite(networkFile);
	}

}
