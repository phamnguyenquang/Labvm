package LogReader;

import java.util.Map;


public class LogProcess {
	
	public String getStatement()
	{
		String log2 = "select * from Logtransform match_recognize( "
				+ "measures A as Logtransform1, B as Logtransform2 pattern (A B) define A as A.authLine.contains('ssh'), B as B.authLine.contains('ssh'))";
		return log2;
	}
	public void update(Map<String, Logtransform>Eventmap)
	{
		Logtransform Logtransform1 = (Logtransform) Eventmap.get("Logtransform");
		Logtransform Logtransform2 = (Logtransform) Eventmap.get("Logtransform");
		
		StringBuffer sb= new StringBuffer();
		sb.append("attack detected");
		System.out.println(sb.toString());
	}
}
