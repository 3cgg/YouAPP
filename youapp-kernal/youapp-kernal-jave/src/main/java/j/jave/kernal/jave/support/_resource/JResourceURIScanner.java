package j.jave.kernal.jave.support._resource;

import j.jave.kernal.jave.support.JScanner;

import java.net.URI;
import java.util.List;

/**
 * load resource in the form of URI expression.
 * @author J
 *
 */
public interface JResourceURIScanner extends JScanner<List<URI>> {

	List<URI> scan();

}
