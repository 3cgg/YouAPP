package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.support._package.JFieldInfoProvider.JFieldInfoGen;

import java.lang.reflect.Field;

public class JDefaultFieldMetaGen implements JFieldInfoGen<JDefaultFieldMeta> {

	@Override
	public JDefaultFieldMeta getInfo(Field field, Class<?> classIncudeField) {
		JDefaultFieldMeta defaultFieldMeta=new JDefaultFieldMeta();
		defaultFieldMeta.setFieldName(field.getName());
		defaultFieldMeta.setAccess(field.getModifiers());
		defaultFieldMeta.setClazz(classIncudeField);
		defaultFieldMeta.setAnnotations(field.getAnnotations());
		defaultFieldMeta.setField(field);
		defaultFieldMeta.setGetterMethodName(JClassUtils.getGetterMethodName(field));
		defaultFieldMeta.setSetterMethodName(JClassUtils.getSetterMethodName(field));
		return defaultFieldMeta;
	}
	
}
