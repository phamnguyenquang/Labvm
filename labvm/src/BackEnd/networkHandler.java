package BackEnd;

public class networkHandler {
	private String IPAddress;
	private CommandExecutor cmd;

	public networkHandler() {
		cmd = new CommandExecutor();
	}

	public void setIPAddress(String IP) {
		IPAddress = IP;
	}
}
