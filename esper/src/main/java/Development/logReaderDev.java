package Development;

import java.util.ArrayList;

public class logReaderDev {
	private ArrayList<String> Messages = new ArrayList<String>();
	private ArrayList<Integer> timeStamp = new ArrayList<Integer>();
	private jsonIO logger;

	public logReaderDev(String path) {
		logger = new jsonIO(path);
		Messages = logger.getMessageLog();
		timeStamp = logger.getTimeStampLog();
		logger.update();
//		System.out.println(logger.size());
	}

	public String MessageAt(int i) {
		return Messages.get(i);
	}

	public Integer getTimeStampAt(int i) {
		return timeStamp.get(i);
	}

	public int size() {
		return Messages.size();
	}

}
