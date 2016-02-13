package j.jave.kernal.jave.xml.xmldb.jql;

public class JOrLink extends JLink {
	public JOrLink() {
		this.compare=OR;
	}

	@Override
	public String jql() {
		return OR;
	}
}
