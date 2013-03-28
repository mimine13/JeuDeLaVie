import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class genereHTML {

	public genereHTML() throws IOException {
	}

	public void openfile(String s) {
		Properties sys = System.getProperties();
		String os = sys.getProperty("os.name");
		System.out.println(os);
		Runtime r = Runtime.getRuntime();
		try {
			if (os.endsWith("NT") || os.endsWith("2000") || os.endsWith("XP")
					|| os.endsWith("Vista")) {
				r.exec("cmd /c start http://fr.wikipedia.org/wiki/Jeu_de_la_vie");
			} else {
				r.exec("firefox .//" + s);
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
	}

	public void writefilehtml() throws IOException {
		FileReader s = new FileReader("html");
		BufferedReader d = new BufferedReader(s);
		FileWriter h = new FileWriter("index.html");
		String line = d.readLine();
		while (line != null) {
			h.write(line);
			line = d.readLine();
		}
		h.write("</body></html>");
		h.close();
		d.close();
		s.close();
	}
}
