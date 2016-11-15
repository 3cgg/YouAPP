package j.jave.kernal.streaming.kryo;

import com.esotericsoftware.kryo.Kryo;

public  abstract class KryoGetter {

	// Setup ThreadLocal of Kryo instances
	private static final ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
	    protected Kryo initialValue() {
	        Kryo kryo = new Kryo();
	        // configure kryo instance, customize settings
	        return kryo;
	    };
	};

	
	public final Kryo getKryo(Class<?> clazz) {
		Kryo kryo= kryos.get();
		affactKryo(clazz);
		return kryo;
	}
	
	abstract void affactKryo(Class<?> clazz);
	
	
	
}
