package j.jave.kernal.jave.base64;

import j.jave.kernal.jave.extension.JExtensionProvider;


public interface JBase64FactoryProvider extends JExtensionProvider {

	public abstract void setBase64Factory(Class<? extends JIBase64Factory> clazz);

	public abstract void setBase64Factory(JIBase64Factory factory);

	public abstract JIBase64Factory getBase64Factory();

}