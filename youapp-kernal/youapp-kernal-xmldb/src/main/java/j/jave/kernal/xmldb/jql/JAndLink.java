package j.jave.kernal.xmldb.jql;


public class JAndLink extends JLink {
	public JAndLink() {
		this.compare=AND;
	}

	@Override
	public String jql() {
		return AND;
	}
}
