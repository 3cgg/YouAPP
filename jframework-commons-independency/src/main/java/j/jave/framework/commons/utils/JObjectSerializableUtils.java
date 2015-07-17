package j.jave.framework.commons.utils;

import j.jave.framework.commons.base64.JBase64Factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * serialize - deserialize
 * @author J
 *
 */
public class JObjectSerializableUtils {

	/**
	 * serialize the object , then encode the bytes by base64
	 * @param obj
	 * @return base64 encoded string
	 */
	public static String serialize(Object obj){
		try {
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			ObjectOutputStream outputStream=new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(obj);
			byte[] bytes=byteArrayOutputStream.toByteArray();
			return JBase64Factory.getBase64().encodeBase64String(bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * deserialize the string to a object
	 * @param string base64 encoded string
	 * @param clazz
	 * @return
	 */
	public static <T> T deserialize(String string,Class<T> clazz){
		try {
			byte[] bytes=JBase64Factory.getBase64().decodeBase64(string);
			ObjectInputStream inputStream=new ObjectInputStream(new ByteArrayInputStream(bytes));
			return clazz.cast(inputStream.readObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * deserialize the byte array to a object
	 * @param bytes
	 * @param clazz
	 * @return
	 */
	public static <T> T deserialize(byte[] bytes,Class<T> clazz){
		try {
			ObjectInputStream inputStream=new ObjectInputStream(new ByteArrayInputStream(bytes));
			return clazz.cast(inputStream.readObject());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * serialize the object to byte array.
	 * @param obj
	 * @return
	 */
	public static byte[] serializeObject(Object obj){
		
		try {
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			ObjectOutputStream outputStream=new ObjectOutputStream(byteArrayOutputStream);
			outputStream.writeObject(obj);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
