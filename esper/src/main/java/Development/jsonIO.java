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
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

public class jsonIO {
	private String path;
	private ArrayList<String> Messages = new ArrayList<String>();
	private ArrayList<Integer> timeStamp = new ArrayList<Integer>();
	private String tempString = "";
	private int tempInt = 0;

	public jsonIO(String pathFile) {
		path = pathFile;
		System.out.println("json reader initiated " + path);
	}

	private void getContent() {
		try {
			InputStream jis = new FileInputStream(path);
			Reader r = new InputStreamReader(jis, "UTF-8");
			Gson gson = new Gson();
			JsonStreamParser p = new JsonStreamParser(r);
			Messages.clear();
			while (p.hasNext()) {
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Map m = gson.fromJson(e, Map.class);
					String mm = (String) m.get("MESSAGE");
					tempString = mm;
					Messages.add(tempString);
					tempInt = Integer.parseInt(((String) m.get("__MONOTONIC_TIMESTAMP")));
					timeStamp.add(tempInt);
				}
			}
			jis.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getMessageLog() {
		getContent();

		return Messages;
	}

	public ArrayList<Integer> getTimeStampLog() {
		return timeStamp;
	}

	public void printMessage() {
		for (int i = 0; i < Messages.size(); ++i) {
			System.out.println("IO " + Messages.get(i));
		}
	}

	public int size() {
		return Messages.size();
	}

	public void update() {
		getContent();
		printMessage();
	}

}
