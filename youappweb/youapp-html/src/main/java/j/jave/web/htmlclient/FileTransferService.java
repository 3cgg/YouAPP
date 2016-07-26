package j.jave.web.htmlclient;

import java.net.URI;
import java.util.Map;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.service.JService;

public interface FileTransferService extends JService {

	URI transfer(JFile file,Map<String, Object> context);
	
}
