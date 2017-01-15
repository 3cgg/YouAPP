package me.bunny.kernel.mock;

import me.bunny.kernel.jave.service.JService;

/**
 * for {@link JJSONMock}, ANNOTATION-DRIVEN.
 * @author J
 *
 */
public interface JJSONMockService extends JService {

	Object mockData(JMockModel mockModel) throws Exception;
	
	boolean accept(JMockModel mockModel);
}
