package j.jave.kernal.taskdriven.tkdd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JTaskMetadataOnTask {

	Class<? extends JTaskMetadata> value() default JMockGlobalMetadata.class ;
	
}
