package j.jave.web.htmlclient.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import me.bunny.kernel.jave.io.JFile;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.utils.JIOUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

public abstract class ServletUtil {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ServletUtil.class);
	
	
	public static final void writeBytesDirectly(HttpServletRequest request,
			HttpServletResponse response,byte[] bytes){
		OutputStream outputStream=null;
		try {
			outputStream=response.getOutputStream();
			response.getOutputStream().write(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * check if the context type is "multipart/form-data" , i.e. file upload...
	 * @param request
	 * @return
	 */
	public static boolean isFileContextType( HttpServletRequest request){
		return (JStringUtils.isNotNullOrEmpty(request.getContentType())
				&&request.getContentType().indexOf("multipart/form-data") != -1);
	}
	
	
	/**
	 * do with request , to extract file which send to file distribute service . 
	 * @param request
	 * @return Map<String, Object>  , empty object, if the request type is not "multipart/form-data" 
	 * or the request does not have  any parameter.  
	 * <p> KEY: parameter key.
	 * <p> VALUE: parameter value(s) 
	 */
	public static Map<String, Object> doWithRequestParameterWithFileAttached( HttpServletRequest request) {
		Map<String, Object> parameters=new HashMap<String, Object>();
		if (isFileContextType(request)) { // file
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			factory.setSizeThreshold(1024 * 8);// 设置8k的缓存空间
//			factory.setRepository(new File("d:/a"));
			ServletFileUpload upload = new ServletFileUpload();
			upload.setHeaderEncoding("utf-8");// 设置文件名处理中文编码
			try {
				FileItemIterator fii = upload.getItemIterator(request);// 使用遍历类
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();
					if (fis.isFormField()) {// FileItemStream同样使用OpenStream获取普通表单的值
						String fieldName=fis.getFieldName();
						String value=new String(JIOUtils.getBytes(fis.openStream()),"utf-8");
						if(parameters.containsKey(fieldName)){
							Object obj=parameters.get(fieldName);
							if(List.class.isInstance(obj)){ 
								((List)obj).add(value);
							}
							else{
								List<String> values=new ArrayList<String>();
								values.add((String) obj);
								values.add(value);
								parameters.put(fieldName, values);
							}
						}else{
							parameters.put(fieldName, value);
						}
					} else {
						String fileName = fis.getName();
						byte[] bytes=JIOUtils.getBytes(fis.openStream());
						File file=new File(fileName);
						JFile jFile=new JFile(file);
						jFile.setFileContent(bytes);
						parameters.put(fis.getFieldName(), jFile);
					}
				}
				
				for (Iterator<Entry<String,Object>> iterator = parameters.entrySet().iterator(); iterator
						.hasNext();) {
					Entry<String,Object> entry = iterator.next();
					if(List.class.isInstance(entry.getValue())){
						entry.setValue(((List)entry.getValue()).toArray(new String[0]));
					}
				}
			} catch (FileUploadException e) {
				LOGGER.error(e.getMessage(), e);
			}catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return parameters;
	}
	
}
