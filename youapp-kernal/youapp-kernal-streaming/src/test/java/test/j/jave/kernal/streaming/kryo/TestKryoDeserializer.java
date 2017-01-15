package test.j.jave.kernal.streaming.kryo;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.bunny.kernel._c.serializer.JJSONSerializerFactory;
import me.bunny.kernel._c.serializer.JJavaObjectSerializerFactory;
import me.bunny.kernel._c.serializer.JSerializer;
import me.bunny.kernel._c.serializer.JSerializerFactory;
import me.bunny.modular._p.streaming.kryo.KryoSerializerFactory;

public class TestKryoDeserializer {

	public static void main(String[] args) throws Exception {
		long time=new Date().getTime();
		JSerializerFactory factory=new KryoSerializerFactory();
		JSerializer serializer=factory.newSerializer(DefaultDeviceRecord.class);
		File file=new File("d:/cp/kryo.bin");
		Object defaultDeviceRecordKryo=serializer.read(new FileInputStream(file), ArrayList.class);
		long now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";DefaultDeviceRecord (KRYO): "+defaultDeviceRecordKryo);
		
		time=new Date().getTime();
		factory=new JJSONSerializerFactory();
		serializer=factory.newSerializer(DefaultDeviceRecord.class);
		file=new File("d:/cp/json.json");
		Object defaultDeviceRecordJSON=serializer.read(new FileInputStream(file), ArrayList.class);
		now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";DefaultDeviceRecord (JSON): "+defaultDeviceRecordJSON);
		
		time=new Date().getTime();
		factory=new JJavaObjectSerializerFactory();
		serializer=factory.newSerializer(DefaultDeviceRecord.class);
		file=new File("d:/cp/java.java");
		Object defaultDeviceRecordJAVA=serializer.read(new FileInputStream(file), ArrayList.class);
		now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";DefaultDeviceRecord (JAVA): "+defaultDeviceRecordJAVA);
		
		
		System.out.println("end");
		
	}
	
	
	public static List<DefaultDeviceRecord> data() {
		Random random=ThreadLocalRandom.current();
		List<DefaultDeviceRecord> defaultDeviceRecords=new ArrayList<>(10000);
		for(int i=0;i<1;i++){
			DefaultDeviceRecord  ddr =  new DefaultDeviceRecord();
			ddr.setSeries("series1");
			ddr.setStar("star2");
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.SECOND, i);
			ddr.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
			List<String> paramKeys =  new ArrayList<String>();
			for(int j=0;j<10;j++){
				paramKeys.add("sequence_00120"+j);
			}
			ddr.setProperties(paramKeys.toArray(new String[]{}));
			List<String> paramValues =  new ArrayList<String>();
			for(int j=0;j<10;j++){
				paramValues.add(
						((random.nextInt(10)==j)?"#":"")
				+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10));
			}
			ddr.setPropertyVals(paramValues.toArray(new String[]{}));
			defaultDeviceRecords.add(ddr);
		}
		return defaultDeviceRecords;
	}
}
