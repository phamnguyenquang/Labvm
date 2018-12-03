package Development;

import java.util.Map;

import LogReader.Logtransform;

public class SSHbruteForceDev {
	public String getStatement()
	{
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('ssh'), B as B.message.contains('ssh'))";
		return log2;
	}
	public void update(Map<String, LogEventDev>Eventmap)
	{
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		StringBuffer sb= new StringBuffer();
		sb.append("ssh attack detected");
		System.out.println(sb.toString());
	}
}
