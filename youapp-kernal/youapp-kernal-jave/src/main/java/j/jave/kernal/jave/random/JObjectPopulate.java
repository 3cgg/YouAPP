package j.jave.kernal.jave.random;

import j.jave.kernal.jave.support.detect.JFieldDetect;
import j.jave.kernal.jave.support.detect.JFieldInfoProvider.JFieldInfo;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class JObjectPopulate  extends JAbstractFieldPopulate{

	private Object object;
	
	public JObjectPopulate(Object object) {
		this.object=object;
	}
	
	@Override
	public void populate() throws Exception {
		
		JFieldInfo<FieldRelated> fieldInfo=new JFieldInfo<FieldRelated>() {
			@Override
			public FieldRelated getInfo(Field field, Class<?> classIncudeField) {
				FieldRelated fieldRelated=new FieldRelated();
				fieldRelated.setField(field);
				fieldRelated.setRandom(getRandom(field,object.getClass()));
				fieldRelated.setObjectClass(object.getClass());
				return fieldRelated;
			}
		};
		JFieldDetect<FieldRelated> fieldDetect=new JFieldDetect<FieldRelated>(fieldInfo);
		fieldDetect.detect(object.getClass());
		
		List<FieldRelated>  fieldRelateds=  fieldDetect.getFieldInfos();
		
		JCollectionUtils.each(fieldRelateds, new JCollectionUtils.CollectionCallback<FieldRelated>() {
			@Override
			public void process(FieldRelated element) throws Exception {
				
				JRandom<?> random=element.getRandom();
				if(random==null) return;
				Object value=null;
				if(random instanceof JFieldRandom<?>){
					value=((JFieldRandom<?>)random).random(element.getField(), element.getObjectClass());
				}
				else{
					value=random.random();
				}
				element.getField().set(object, value);
			}
		});
		
	}
	
	
	
	
}
