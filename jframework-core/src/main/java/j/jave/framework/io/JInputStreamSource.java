package j.jave.framework.io;

import java.io.IOException;
import java.io.InputStream;

public interface JInputStreamSource {

	InputStream getInputStream() throws IOException;
}
