package j.jave.framework.commons.io;

import java.io.IOException;
import java.io.InputStream;

public interface JInputStreamSource {

	InputStream getInputStream() throws IOException;
}
