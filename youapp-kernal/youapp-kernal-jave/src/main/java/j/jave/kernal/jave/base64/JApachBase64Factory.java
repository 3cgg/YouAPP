package j.jave.kernal.jave.base64;


class JApachBase64Factory implements JIBase64Factory {

	private static JApachBase64Factory apachBase64Factory;
	
	private volatile boolean available=false;
	
	private volatile boolean availableTested=false;
	
	private Object sync=new Object();
	
	private JApachBase64Factory() {
	}
	
	static JApachBase64Factory get(){
		if(apachBase64Factory==null){
			synchronized (JApachBase64Factory.class) {
				if(apachBase64Factory==null){
					apachBase64Factory=new JApachBase64Factory();
				}
			}
		}
		return apachBase64Factory;
	}
	
	@Override
	public JBase64 getBase64() {
		return JApacheBase64Wrapper.get();
	}

	
	@Override
	public boolean available() {

		if(!availableTested){
			synchronized (sync) {
				if(!availableTested){
					try{
						JApacheBase64Wrapper.get().decodeBase64(JApachBase64Factory.class.getName());
						available=true;
					}catch(Throwable e){
						available=false;
					}
					availableTested=true;
				}
			}
		}
		return available;
	
	}

}
