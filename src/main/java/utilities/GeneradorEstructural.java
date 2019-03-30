
package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class GeneradorEstructural {

	List<String>	a				= new ArrayList<String>();
	static String	keyword			= "Question";
	static String	rutaEstructura	= "C:/Documents and Settings/Student/Desktop/generator/copy/" + GeneradorEstructural.keyword + "Service.java";
	static String	rutaDestino		= "C:/Documents and Settings/Student/Desktop/generator/servis";

	static String	rutaEstructura2	= "C:/Documents and Settings/Student/Desktop/generator/copy/" + GeneradorEstructural.keyword + "Repository.java";
	static String	rutaDestino2	= "C:/Documents and Settings/Student/Desktop/generator/repos";

	static String	rutaEstructura3	= "C:/Documents and Settings/Student/Desktop/generator/copy/" + GeneradorEstructural.keyword + "ToStringConverter.java";
	static String	rutaDestino3	= "C:/Documents and Settings/Student/Desktop/generator/convers";

	static String	rutaEstructura4	= "C:/Documents and Settings/Student/Desktop/generator/copy/StringTo" + GeneradorEstructural.keyword + "Converter.java";
	static String	rutaDestino4	= "C:/Documents and Settings/Student/Desktop/generator/convers";


	public static void main(final String[] args) {

		final GeneradorEstructural fr = new GeneradorEstructural();
		fr.doIt();
		GeneradorEstructural.rutaEstructura = GeneradorEstructural.rutaEstructura2;
		GeneradorEstructural.rutaDestino = GeneradorEstructural.rutaDestino2;
		fr.doIt();
		GeneradorEstructural.rutaEstructura = GeneradorEstructural.rutaEstructura3;
		GeneradorEstructural.rutaDestino = GeneradorEstructural.rutaDestino3;
		fr.doIt();
		GeneradorEstructural.rutaEstructura = GeneradorEstructural.rutaEstructura4;
		GeneradorEstructural.rutaDestino = GeneradorEstructural.rutaDestino4;
		fr.doIt();

	}
	public void doIt() {
		this.a.add("Manager");
		this.a.add("Actor");
		this.a.add("Administrator");
		this.a.add("Buy");
		this.a.add("ConfigurationSystem");
		this.a.add("Customer");
		this.a.add("DomainEntity");
		this.a.add("Message");
		this.a.add("MessageFolder");
		this.a.add("Picture");
		this.a.add("Reclamation");
		this.a.add("Tag");
		this.a.add("Tattoo");
		this.a.add("Tattooist");

		final File f1 = new File(GeneradorEstructural.rutaEstructura);
		final String lowKeyword = GeneradorEstructural.keyword.substring(0, 1).toLowerCase() + GeneradorEstructural.keyword.substring(1);
		for (final String st : this.a) {
			final String lowst = st.substring(0, 1).toLowerCase() + st.substring(1);
			final List<String> lines = new ArrayList<String>();
			String line = null;
			try {
				final FileReader fr = new FileReader(f1);
				final BufferedReader br = new BufferedReader(fr);
				while ((line = br.readLine()) != null) {
					if (line.contains(GeneradorEstructural.keyword))
						line = line.replace(GeneradorEstructural.keyword, st);
					if (line.contains(lowKeyword))
						line = line.replace(lowKeyword, lowst);
					lines.add(line + "\r\n");
				}
				fr.close();
				br.close();
				String fileName = f1.getName().replace(GeneradorEstructural.keyword, st);
				System.out.println(fileName + " generated.");
				fileName = fileName.replace(lowKeyword, lowst);
				final FileWriter fw = new FileWriter(GeneradorEstructural.rutaDestino + "/" + fileName);
				final BufferedWriter out = new BufferedWriter(fw);
				for (final String s : lines)
					out.write(s);
				out.flush();
				out.close();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
