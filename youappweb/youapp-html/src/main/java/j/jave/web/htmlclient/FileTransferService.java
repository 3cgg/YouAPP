package j.jave.web.htmlclient;

import java.net.URI;
import java.util.Map;

import me.bunny.kernel.jave.io.JFile;
import me.bunny.kernel.jave.service.JService;

public interface FileTransferService extends JService {

	URI transfer(JFile file,Map<String, Object> context);
	
}
