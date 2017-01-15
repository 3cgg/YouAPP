package me.bunny.kernel._c.xml.xmldb.jql;

public class JOrLink extends JLink {
	public JOrLink() {
		this.compare=OR;
	}

	@Override
	public String jql() {
		return OR;
	}
}
