package j.jave.platform.basicwebcomp.web.proext.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@PropertyExtendable
public @interface PropertyExtend {
	
	ObjectExtend objectExtend() default @ObjectExtend(property="null",active=false );
	
	CodeExtend codeExtend() default @CodeExtend(property="null",codeType="null",active=false) ;
	
	boolean active() default true;
}
