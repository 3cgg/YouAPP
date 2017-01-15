package me.bunny.kernel._c.support._package;

import java.lang.reflect.Field;

import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.support._package.JFieldInfoProvider.JFieldInfoGen;

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
