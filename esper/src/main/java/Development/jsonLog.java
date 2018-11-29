package Development;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

public class jsonLog {
	private String path;

	public jsonLog(String pathFile) {
		path = pathFile;
	}

	public void printContent() {
		try {
			InputStream jis = new FileInputStream(path);
			Reader r = new InputStreamReader(jis, "UTF-8");
			Gson gson = new GsonBuilder().create();
			JsonStreamParser p = new JsonStreamParser(r);

			while (p.hasNext()) {
				JsonElement e = p.next();
				if (e.isJsonObject()) {
					Map m = gson.fromJson(e, Map.class);
					System.out.println(m.get("MESSAGE"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
