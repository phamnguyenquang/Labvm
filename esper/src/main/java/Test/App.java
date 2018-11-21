package Test;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import LogReader.LogEvent;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider();
		engine.getEPAdministrator().getConfiguration().addEventType(LogEvent.class);
		
		String log = "";

	}

}
