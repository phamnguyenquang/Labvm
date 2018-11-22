package Test;

import java.util.ArrayList;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import LogReader.LogCombi;
import LogReader.LogEvent;
import LogReader.LogProcess;

public class App {

	public static void main(String[] args) {

		/*
		 * Preserve the log by copying it into ArrayList<String> line by line everytime
		 * "sudo cat /var/log/*.log" is called, root session is logged first, then the
		 * command is executed
		 */

		LogCombi logCombination = new LogCombi();
		int i = logCombination.authLength();

		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		engine.getEPAdministrator().getConfiguration().addEventType(LogProcess.class);

		String log = "select authLine from LogProcess";
		EPStatement logStatement = engine.getEPAdministrator().createEPL(log);

		logStatement.addListener((newData, oldData) -> {
			String test = (String) newData[0].get("authLine");
			System.out.println(test);
		});
		for (int i1 = 0; i1 < i; ++i1) {
			engine.getEPRuntime().sendEvent(new LogProcess(logCombination, i1));
		}

	}

}
