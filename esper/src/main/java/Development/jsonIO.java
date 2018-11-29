package Development;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

public class jsonIO {
	private String path;
	private ArrayList<String>Messages=new ArrayList<String>();
	private ArrayList<Integer>timeStamp=new ArrayList<Integer>();

	public jsonIO(String pathFile) {
		path = pathFile;
	}

	public void printContent() {
		try {
			InputStream jis = new FileInputStream(path);
			Reader r = new InputStreamReader(jis, "UTF-8");
			Gson gson = new GsonBuilder().create();
			JsonStreamParser p = new JsonStreamParser(r);
			Messages.clear();
			timeStamp.clear();
			
			while (p.hasNext()) {
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Map m = gson.fromJson(e, Map.class);
					System.out.println(m.get("MESSAGE"));
					Messages.add((String) m.get("MESSAGES"));
					timeStamp.add(Integer.parseInt(m.get("__MONOTONIC_TIMESTAMP").toString()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<String> getMessageLog() {
		return Messages;
	}
	public ArrayList<Integer> getTimeStampLog() {
		return timeStamp;
	}
	
}
