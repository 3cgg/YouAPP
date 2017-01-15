package me.bunny.kernel.jave.support._resource;

import java.net.URI;
import java.util.List;

import me.bunny.kernel.jave.support.JScanner;

/**
 * load resource in the form of URI expression.
 * @author J
 *
 */
public interface JResourceURIScanner extends JScanner<List<URI>> {

	List<URI> scan();

}
