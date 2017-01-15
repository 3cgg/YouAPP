package me.bunny.kernel.jave.support.random;

import java.lang.reflect.Field;
import java.util.List;

import me.bunny.kernel.jave.support._package.JAbstractFieldFinder;
import me.bunny.kernel.jave.support._package.JFieldOnSingleClassFinder;
import me.bunny.kernel.jave.support._package.JFieldInfoProvider.JFieldInfoGen;
import me.bunny.kernel.jave.utils.JCollectionUtils;

public class JSimpleObjectRandomBinder  extends JAbstractClassFieldBinder{
	
	@Override
	public void bind(final Object object) throws Exception {
		
		JFieldInfoGen<FieldRelated> fieldInfo=new JFieldInfoGen<FieldRelated>() {
			@Override
			public FieldRelated getInfo(Field field, Class<?> classIncudeField) {
				FieldRelated fieldRelated=new FieldRelated();
				fieldRelated.setField(field);
				fieldRelated.setRandom(getRandom(field,object.getClass()));
				fieldRelated.setObjectClass(object.getClass());
				return fieldRelated;
			}
		};
		JAbstractFieldFinder<FieldRelated> fieldFinder=new JFieldOnSingleClassFinder<FieldRelated>(object.getClass());
		fieldFinder.setFieldInfo(fieldInfo);
		
		List<FieldRelated>  fieldRelateds=  fieldFinder.find().getFieldInfos();
		
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
