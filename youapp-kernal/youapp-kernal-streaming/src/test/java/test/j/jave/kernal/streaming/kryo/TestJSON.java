package test.j.jave.kernal.streaming.kryo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import me.bunny.kernel.jave.serializer.JJSONSerializerFactory;
import me.bunny.kernel.jave.serializer.JSerializer;

public class TestJSON {

	public static void main(String[] args) throws Exception {
		
		JJSONSerializerFactory factory=new JJSONSerializerFactory();
		JSerializer serializer=factory.newSerializer(DefaultDeviceRecord.class);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
		List<DefaultDeviceRecord> defaultDeviceRecords = data();
		for(DefaultDeviceRecord defaultDeviceRecord:defaultDeviceRecords){
			serializer.write(outputStream, defaultDeviceRecord);
		}
		outputStream.writeTo(new FileOutputStream("d:/cp/json.json"));
		outputStream.flush();
		
	}

	public static List<DefaultDeviceRecord> data() {
		Random random=ThreadLocalRandom.current();
		List<DefaultDeviceRecord> defaultDeviceRecords=new ArrayList<>(10000);
		for(int i=0;i<100000;i++){
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
