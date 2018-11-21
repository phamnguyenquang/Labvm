package LogReader;

public class LogEvent {
	private String logLine="";
	private LogReader logRead;

	public LogEvent(String path) {
		logRead = new LogReader(path);
	}

	public String getLogLine() {
		logLine = logRead.getLastLogLine();
		return logLine;
	}
}
