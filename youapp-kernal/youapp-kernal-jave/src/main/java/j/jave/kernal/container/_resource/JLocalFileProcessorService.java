package j.jave.kernal.container._resource;

import j.jave.kernal.container.JExecutableURIUtil;
import j.jave.kernal.container.JExecutableURIUtil.Type;
import j.jave.kernal.container.JURIInfo;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.support._resource.JResourceNotFoundException;
import j.jave.kernal.jave.support._resource.JResourceStreamException;
import j.jave.kernal.jave.utils.JIOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class JLocalFileProcessorService implements JResourceProcessorService {
	
	private Object get(JURIInfo uriInfo,Object object){
		String queryPath=uriInfo.getQueryPath();
		byte[] bytes=null;
		try{
			File file=new File(new URI(queryPath));
			if(!file.exists()){
				throw new JResourceNotFoundException(queryPath);
			}
			bytes=JIOUtils.getBytes(new FileInputStream(file));
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
		return bytes;
	}
	
	
	private boolean put(JURIInfo uriInfo,Object object){
		String queryPath=uriInfo.getQueryPath();
		try{
			File file=new File(new URI(queryPath));
			File parent=file.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			JIOUtils.write(file,(byte[]) object);
			return true;
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
	}
	
	
	private boolean exists(JURIInfo uriInfo,Object object){
		String queryPath=uriInfo.getQueryPath();
		try{
			File file=new File(new URI(queryPath));
			return file.exists();
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
	}
	
	private boolean delete(JURIInfo uriInfo,Object object){
		String queryPath=uriInfo.getQueryPath();
		try{
			File file=new File(new URI(queryPath));
			return file.delete();
		}catch(Exception e){
			throw new JResourceStreamException(queryPath,e);
		}
	}
	
	@Override
	public Object process(URI uri,Object object) {
		JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
		if(Type.GET.getValue().equals(uriInfo.getPath())){
			return get(uriInfo, object);
		}
		if(Type.PUT.getValue().equals(uriInfo.getPath())){
			return put(uriInfo, object);
		}
		if(Type.EXIST.getValue().equals(uriInfo.getPath())){
			return exists(uriInfo, object);
		}
		if(Type.DELETE.getValue().equals(uriInfo.getPath())){
			return delete(uriInfo, object);
		}
		throw new JOperationNotSupportedException("cannot process uri : "+uriInfo.getWholeUri());
	}

}
