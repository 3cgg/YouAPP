package j.jave.framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class JUtils {

	/**
	 * extract stream in the form of byte. 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
	
	
	public static boolean hasInArray(Object[] object) {
		if (object == null)
			return false;
		else
			return object.length > 0;
	}

	public static boolean hasInCollect(Collection object) {
		if (object == null)
			return false;
		else
			return object.size() > 0;
	}

	public static boolean hasInMap(Map object) {
		if (object == null)
			return false;
		else
			return object.size() > 0;
	}

	public static boolean hasInbytes(byte[] bytes) {
		if (bytes == null)
			return false;
		else
			return bytes.length > 0;
	}

	public static boolean exceedLength(String string, int size) {
		if (string == null)
			return false;
		else
			return string.length() > size;
	}

	public static String unique() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * this method always be invoked before stringtobytes public static byte[]
	 * stringtobytes(String string)
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytestoBASE64String(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * this method always be invoked after bytestoString public static String
	 * bytestoString(byte[] bytes)
	 *
	 * @param string
	 * @return
	 */
	public static byte[] base64stringtobytes(String string) {
		try {
			return new BASE64Decoder().decodeBuffer(string);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void copyFile(File file, String fullPath) {
		try {
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(fullPath);
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] filetobytes(File file) {
		try {

			if (file == null || !file.isFile())
				return null;

			ByteArrayOutputStream bos = null;
			FileInputStream fis = null;
			try {
				bos = new ByteArrayOutputStream();
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public static boolean gtEqualNumber(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		try{
			double beforeValue=Double.valueOf(String.valueOf(before));
			double afterValue=Double.valueOf(String.valueOf(after));
			return beforeValue>afterValue;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean ltEqualNumber(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		try{
			double beforeValue=Double.valueOf(String.valueOf(before));
			double afterValue=Double.valueOf(String.valueOf(after));
			return beforeValue<afterValue;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public static boolean gtEqualString(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		return String.valueOf(before).compareTo(String.valueOf(after))>=0;
	}

	public static boolean ltEqualString(Object before,Object after){

		if(before==null&&after==null) return true;

		if(before==null) return false;

		if(after==null) return true;

		return String.valueOf(before).compareTo(String.valueOf(after))<=0;
	}


	public static boolean isNumber(String string){
		try{
			Double.valueOf(string);
		}catch (NumberFormatException  e) {
			return false;
		}
		return true;
	}
	
	public static boolean isNullOrEmpty(String string){
		return string==null||"".equals(string.trim());
	}
	
	public static boolean isNotNullOrEmpty(String string){
		return string!=null&&!"".equals(string.trim());
	}
	
	/**
	 * format {@link Timestamp} to yyyy-MM-dd.
	 * @param timestamp
	 * @return 
	 */
	public static String format(Date date){
		return new SimpleDateFormat("yyyy-MM-dd").format(date) ;
	}

	/**
	 * format {@link Timestamp} to yyyy-MM-dd HH:mm:ss.
	 * @param timestamp
	 * @return 
	 */
	public static String formatWithSeconds(Timestamp timestamp){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp) ;
	}
	
	/**
	 * 返回距离当前日期的时间差。
	 * 比如： 5分钟之前，1小时之前，2年之前等等
	 * @param timestamp
	 * @return
	 */
	public static String getTimeOffset(Timestamp timestamp){
		String desc="";
		int minute=60000;
		int hour=minute*60;
		int day=minute*60*24;
		long month=day*30;
		long year=month*12;
		long timeOffset=Calendar.getInstance().getTime().getTime()- timestamp.getTime();
		long div=-1;
		if((div=timeOffset/year)>0){
			desc=div+"年之前";
		}
		else if((div=timeOffset/month)>0){
			desc=div+"月之前";
		}
		else if((div=timeOffset/day)>0){
			desc=div+"天之前";
		}
		else if((div=timeOffset/hour)>0){
			desc=div+"小时之前";
		}
		else if((div=timeOffset/minute)>0){
			desc=div+"分钟之前";
		}
		return desc;
	}
	
	/**
	 * date format is "yyyy-MM-dd"
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public static Date parseDate(String date) throws Exception{
		return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	}
	
	/**
	 * date format is "yyyy-MM-dd"
	 * @param date
	 * @return
	 * @throws Exception 
	 */
	public static Timestamp parseTimestamp(String date) throws Exception{
		return new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
	}
	
	
	public static double toDouble(String value){
		if(isNullOrEmpty(value)){
			return 0;
		}else{
			return Double.valueOf(value).doubleValue();
		}
	}
	
	public static int toInt(String value){
		if(isNullOrEmpty(value)){
			return 0;
		}else{
			return Integer.valueOf(value).intValue();
		}
	}
	
	public static long toLong(String value){
		if(isNullOrEmpty(value)){
			return 0;
		}else{
			return Long.valueOf(value).longValue();
		}
	}
	
	public static String toString(Object object){
		if(object==null){
			return "";
		}
		else{
			return String.valueOf(object);
		}
	}
	
	
}
