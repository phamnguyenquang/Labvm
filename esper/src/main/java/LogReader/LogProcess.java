package LogReader;

import java.util.ArrayList;

public class LogProcess {
	private String authLine = "";
	private String sysLine = "";

	public LogProcess(LogCombi log, int i) {
		authLine = log.authLine(i);
		sysLine = log.sysLine(i);
	}

	public String getAuthLine() {
		return authLine;
	}

	public String getSysLine() {
		return sysLine;
	}

}
