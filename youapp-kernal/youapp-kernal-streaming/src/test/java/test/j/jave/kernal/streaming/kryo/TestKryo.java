package test.j.jave.kernal.streaming.kryo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import j.jave.kernal.streaming.kryo.KryoSerializerFactory;
import me.bunny.kernel.jave.serializer.JJSONSerializerFactory;
import me.bunny.kernel.jave.serializer.JJavaObjectSerializerFactory;
import me.bunny.kernel.jave.serializer.JSerializer;
import me.bunny.kernel.jave.serializer.JSerializerFactory;

public class TestKryo {

	public static void main(String[] args) throws Exception {
		List<DefaultDeviceRecord> defaultDeviceRecords = data();
		
		long time=new Date().getTime();
		JSerializerFactory factory=new KryoSerializerFactory();
		JSerializer serializer=factory.newSerializer(DefaultDeviceRecord.class);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
//		for(DefaultDeviceRecord defaultDeviceRecord:defaultDeviceRecords){
//			serializer.write(outputStream, defaultDeviceRecord);
//		}
		serializer.write(outputStream, defaultDeviceRecords);
		File file=new File("d:/cp/kryo.bin");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		outputStream.writeTo(new FileOutputStream(file));
		outputStream.flush();
		long now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";Kryo");
		
		time=new Date().getTime();
		factory=new JJSONSerializerFactory();
		serializer=factory.newSerializer(DefaultDeviceRecord.class);
		outputStream = new ByteArrayOutputStream(1024);
//		for(DefaultDeviceRecord defaultDeviceRecord:defaultDeviceRecords){
//			serializer.write(outputStream, defaultDeviceRecord);
//		}
		serializer.write(outputStream, defaultDeviceRecords);
		file=new File("d:/cp/json.json");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		outputStream.writeTo(new FileOutputStream(file));
		outputStream.flush();
		now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";JSON");
		
		time=new Date().getTime();
		factory=new JJavaObjectSerializerFactory();
		serializer=factory.newSerializer(DefaultDeviceRecord.class);
		outputStream = new ByteArrayOutputStream(1024);
//		for(DefaultDeviceRecord defaultDeviceRecord:defaultDeviceRecords){
//			serializer.write(outputStream, defaultDeviceRecord);
//		}
		serializer.write(outputStream, defaultDeviceRecords);
		file=new File("d:/cp/java.java");
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		outputStream.writeTo(new FileOutputStream(file));
		outputStream.flush();
		now=new Date().getTime(); 
		System.out.println(" time : "+(now-time)+";JAVA");
		
		System.out.println("end");
		
	}
	
	
	public static List<DefaultDeviceRecord> data() {
		Random random=ThreadLocalRandom.current();
		List<DefaultDeviceRecord> defaultDeviceRecords=new ArrayList<>(10000);
		for(int i=0;i<10;i++){
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
