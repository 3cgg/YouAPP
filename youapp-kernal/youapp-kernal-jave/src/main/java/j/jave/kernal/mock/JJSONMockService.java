package j.jave.kernal.mock;

import j.jave.kernal.jave.service.JService;

/**
 * for {@link JJSONMock}, ANNOTATION-DRIVEN.
 * @author J
 *
 */
public interface JJSONMockService extends JService {

	Object mockData(JMockModel mockModel) throws Exception;
	
	boolean accept(JMockModel mockModel);
}
