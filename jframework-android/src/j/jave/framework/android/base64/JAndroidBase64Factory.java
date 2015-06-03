package j.jave.framework.android.base64;

import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.extension.base64.JIBase64Factory;
import android.util.Base64;

public class JAndroidBase64Factory implements JIBase64Factory {

	private static class Base64Wrapper implements JBase64{

		@Override
		public byte[] decodeBase64(String string) {
			return Base64.decode(string, Base64.DEFAULT);
		}

		@Override
		public String encodeBase64String(byte[] bytes) {
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		}

		@Override
		public String encodeBase64URLSafeString(byte[] bytes) {
			return Base64.encodeToString(bytes, Base64.URL_SAFE);
		}
	}
	
	private static Base64Wrapper base64Wrapper=new Base64Wrapper();
	
	@Override
	public JBase64 getBase64() {
		return base64Wrapper;
	}

}
