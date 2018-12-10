package Development;

import java.util.Map;

import LogReader.Logtransform;

public class ShadowBreachDev {
	public String getStatement()
	{
		String log2 = "select * from LogEventDev match_recognize( "
				+ "measures A as LogEventDev1, B as LogEventDev2 pattern (A B) define A as A.message.contains('session opened for user root'), B as B.message.contains('/etc/shadow')";
		return log2;
	}
	public void update(Map<String, LogEventDev>Eventmap)
	{
		LogEventDev LogEventDev1 = (LogEventDev) Eventmap.get("LogEventDev1");
		LogEventDev LogEventDev2 = (LogEventDev) Eventmap.get("LogEventDev2");
		StringBuffer sb= new StringBuffer();
		sb.append("shadow password file was tempted, was that intentional?");
		System.out.println(sb.toString());
	}
}
