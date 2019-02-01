package BackEnd_Misc;

public class SHA512Handler {
	private String inputPass;
	private String user;
	private String userPass;
	private String codedInputPass;
	private String trimmedPass;
	private CommandExecutor linux = new CommandExecutor();

	public SHA512Handler(String iPass) {
		inputPass = iPass;
		doWork();
	}

	private void doWork() {
		linux.startCommand("whoami");
		user = linux.getResult();
		linux.startCommand("sudo cat /etc/shadow | grep " + user);
		userPass = linux.getResult();
		trimmedPass = "";
		for (int i = user.length() + 2; i < userPass.length(); ++i) {
			if (userPass.charAt(i) == ':') {
				trimmedPass = userPass.substring(user.length() + 1, i);
				break;
			}
		}
		System.out.println(trimmedPass);
		String salt = "";
		int index = userPass.length();
		for (int i = 3; i < userPass.length(); ++i) {
			if (userPass.charAt(i) == '$') {
				index = i;
			}
		}
		salt = userPass.substring((user.length() + 4), index);
		linux.startCommand("mkpasswd -m sha-512 " + inputPass + " -s " + salt + "");
		codedInputPass = linux.getResult();
		System.out.println(codedInputPass);
	}

	public boolean isMatch() {
		if (codedInputPass.equals(trimmedPass)) {
			return true;
		} else {
			return false;
		}
	}
}
