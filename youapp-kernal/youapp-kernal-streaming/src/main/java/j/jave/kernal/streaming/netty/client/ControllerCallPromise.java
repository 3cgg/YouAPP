package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * combined with {@link IntarfaceImpl#asyncProxy()} to support ASYNC RPC.
 * @author JIAZJ
 *
 * @param <V>
 * @see ControllerAsyncCall
 */
public class ControllerCallPromise<V> implements CallPromise<V>{

	private final CallPromise callPromise;
	
	private ControllerAsyncCall controllerAsyncCall;
	
	private IntarfaceImpl intarfaceImpl;
	
	private Object proxy;
	
	private Method method;
	
	private Object[] args;
	
	public ControllerCallPromise(CallPromise callPromise) {
		this.callPromise =callPromise;
	}
	
	public void setControllerAsyncCall(ControllerAsyncCall controllerAsyncCall) {
		this.controllerAsyncCall = controllerAsyncCall;
	}
	
	ControllerAsyncCall getControllerAsyncCall() {
		return controllerAsyncCall;
	}
	
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	public void setIntarfaceImpl(IntarfaceImpl intarfaceImpl) {
		this.intarfaceImpl = intarfaceImpl;
	}
	@Override
	public boolean isCancelled() {
		return callPromise.isCancelled();
	}

	@Override
	public boolean isDone() {
		return callPromise.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		Object object=callPromise.get();
		return (V) intarfaceImpl.deserialize(object, method, args, object);
	}

	@Override
	public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		Object object=callPromise.get(timeout, unit);
		return (V) intarfaceImpl.deserialize(object, method, args, object);
	}

	@Override
	public boolean isRequestSuccess() {
		return callPromise.isRequestSuccess();
	}

	@Override
	public boolean isResponsed() {
		return callPromise.isResponsed();
	}

	@Override
	public boolean isRequestCancellable() {
		return callPromise.isRequestCancellable();
	}

	@Override
	public Throwable cause() {
		return callPromise.cause();
	}

	@Override
	public boolean setRequestSuccess() {
		return callPromise.setRequestSuccess();
	}

	@Override
	public CallPromise<V> setResponse(V result) {
		return callPromise.setResponse(result);
	}

	@Override
	public boolean tryResponse(V result) {
		return callPromise.tryResponse(result);
	}

	@Override
	public CallPromise<V> setFailure(Throwable cause) {
		return callPromise.setFailure(cause);
	}

	@Override
	public boolean tryFailure(Throwable cause) {
		return callPromise.tryFailure(cause);
	}

	@Override
	public boolean setRequestUncancellable() {
		return callPromise.setRequestUncancellable();
	}

	@Override
	public CallPromise<V> addListener(GenericPromiseListener<? extends CallPromise<? super V>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallPromise<V> addListeners(GenericPromiseListener<? extends CallPromise<? super V>>... listeners) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallPromise<V> removeListener(GenericPromiseListener<? extends CallPromise<? super V>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallPromise<V> removeListeners(GenericPromiseListener<? extends CallPromise<? super V>>... listeners) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallPromise<V> await() throws InterruptedException {
		return callPromise.await();
	}

	@Override
	public CallPromise<V> awaitUninterruptibly() {
		return callPromise.awaitUninterruptibly();
	}

	@Override
	public CallPromise<V> sync() throws InterruptedException {
		return callPromise.sync();
	}

	@Override
	public CallPromise<V> syncUninterruptibly() {
		return callPromise.syncUninterruptibly();
	}

	@Override
	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return callPromise.await(timeout, unit);
	}

	@Override
	public boolean await(long timeoutMillis) throws InterruptedException {
		return callPromise.await(timeoutMillis);
	}

	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		return callPromise.awaitUninterruptibly(timeout, unit);
	}

	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) {
		return callPromise.awaitUninterruptibly(timeoutMillis);
	}

	@Override
	public V getNow() {
		Object object=callPromise.getNow();
		return (V) intarfaceImpl.deserialize(object, method, args, object);
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return callPromise.cancel(mayInterruptIfRunning);
	}

	CallPromise getCallPromise() {
		return callPromise;
	}
}
