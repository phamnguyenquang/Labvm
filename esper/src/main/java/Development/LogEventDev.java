package Development;

public class LogEventDev {
	private String message = "";
	private int Time = 0;

	public LogEventDev(logReaderDev reader, int i) {
		message = reader.MessageAt(i);
		Time = reader.getTimeStampAt(i);
	}

	public String getMessage() {
		return message;
	}

	public int getTime() {
		return Time;
	}
}
