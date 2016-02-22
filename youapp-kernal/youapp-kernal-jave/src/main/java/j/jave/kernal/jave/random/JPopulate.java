package j.jave.kernal.jave.random;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * automatically populate the resource , such as class/object instance.
 * @author J
 *
 */
public interface JPopulate {

	public void populate(Object object) throws Exception;
	
	public static  class JDefaultTimestampRandom implements JTimestampRandom {
		@Override
		public Timestamp random() {
			int dayRang=10;
			Long now=new Date().getTime();
			Random random=new Random(now);
			int on=random.nextInt(dayRang);
			while(on==0){
				on=random.nextInt(dayRang);
			}
			int down=random.nextInt(dayRang);
			while(down==0){
				down=random.nextInt(dayRang);
			}
			Long milliseconds= (long) (on* (24*60*60*1000)/down);
			
			return new Timestamp(now+(random.nextBoolean()?1:-1)*milliseconds);
		}
	}
	
	
	public static class JDefaultFloatRandom implements JFloatRandom {
		@Override
		public Float random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return random.nextFloat();
		}
	}
	
	public static  class JDefaultDoubleRandom implements JDoubleRandom {
		@Override
		public Double random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return random.nextDouble();
		}
	}
	
	public static  class JDefaultBooleanRandom implements JBooleanRandom {
		@Override
		public Boolean random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return random.nextBoolean();
		}
	}

	
	public static  class JDefaultDateRandom implements JDateRandom {
		@Override
		public Date random() {
			int dayRang=10;
			Long now=new Date().getTime();
			Random random=new Random(now);
			
			int on=random.nextInt(dayRang);
			while(on==0){
				on=random.nextInt(dayRang);
			}
			int down=random.nextInt(dayRang);
			while(down==0){
				down=random.nextInt(dayRang);
			}
			Long milliseconds= (long) (on* (24*60*60*1000)/down);
			return new Date(now+(random.nextBoolean()?1:-1)*milliseconds);
		}
	}
	
	
	public static  class JDefaultIntRandom implements JIntRandom {
		@Override
		public Integer random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return random.nextInt(Integer.MAX_VALUE);
		}
	}
	
	public static class JDefaultFieldTypeRandom implements JRandom<Object>{
		private Class<?> clazz;
		public JDefaultFieldTypeRandom(Class<?> clazz) {
			this.clazz=clazz;
		}
		
		@Override
		public Object random() {
			try{
				return clazz.newInstance();
			}catch(Exception e){
				throw new JInitializationException(e);
			}
			
		}
	}
	
	public static  class JDefaultLongRandom implements JLongRandom {
		@Override
		public Long random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return random.nextLong();
		}
	}
	
	public static  class JDefaultStringRandom implements JStringRandom{
		@Override
		public String random() {
			return JUniqueUtils.unique();
		}
	}
	
	public static  class JDefaultBigDecimalRandom implements JBigDecimalRandom {
		@Override
		public BigDecimal random() {
			Long now=new Date().getTime();
			Random random=new Random(now);
			return new BigDecimal(random.nextDouble());
		}
	}
	
}
