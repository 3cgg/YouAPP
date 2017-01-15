package me.bunny.kernel.taskdriven.tkdd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JTaskMetadataHierarchy {

	public enum Hierarchy{
		ROOT,CHILD,PARENT
	}
	
	Hierarchy hierarchy() default Hierarchy.ROOT;
	
	Class<? extends JTaskMetadataSpecInit> custom() default JTaskMetadataSpecInit.class;
	
}
