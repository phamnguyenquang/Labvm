package Development;

import java.util.ArrayList;

public class logReader {
	private ArrayList<String>Messages;
	private ArrayList<Integer>timeStamp;
	private jsonIO logger;
	
	public logReader(String path)
	{
		logger = new jsonIO(path);
		Messages = logger.getMessageLog();
		timeStamp = logger.getTimeStampLog();
	}
	public String MessageAt(int i)
	{
		return Messages.get(i);
	}
	public int getTimeStampAt(int i)
	{
		return timeStamp.get(i);
	}
	
}
