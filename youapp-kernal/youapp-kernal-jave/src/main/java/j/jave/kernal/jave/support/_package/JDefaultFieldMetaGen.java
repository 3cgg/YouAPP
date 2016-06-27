package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.support._package.JFieldInfoProvider.JFieldInfoGen;

import java.lang.reflect.Field;

public class JDefaultFieldMetaGen implements JFieldInfoGen<JDefaultFieldMeta> {

	@Override
	public JDefaultFieldMeta getInfo(Field field, Class<?> classIncudeField) {
		JDefaultFieldMeta defaultFieldMeta=new JDefaultFieldMeta();
		defaultFieldMeta.setFieldName(field.getName());
		defaultFieldMeta.setAccess(field.getModifiers());
		defaultFieldMeta.setClazz(field.getType());
		defaultFieldMeta.setAnnotations(field.getAnnotations());
		defaultFieldMeta.setField(field);
		return defaultFieldMeta;
	}
	
}
