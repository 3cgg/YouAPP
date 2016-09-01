package j.jave.platform.webcomp.core.service;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.data.web.model.SimplePageRequest;

import org.springframework.data.domain.Page;

public class ServiceSupportUtil {

	public static <M extends JModel> JPage<M> toJPage(Page<M> returnPage,JPageable pageable){
		JPageImpl<M> page=new JPageImpl<M>();
		page.setContent(returnPage.getContent());
		page.setTotalRecordNumber(returnPage.getTotalElements());
		page.setTotalPageNumber(returnPage.getTotalPages()-1);
		JSimplePageable pageRequest=(JSimplePageable)pageable;
		pageRequest.setPageNumber(returnPage.getNumber());
		page.setPageable(pageable);
		return page;
	}
	
	public static SimplePageRequest toPageRequest(JPageable pageable){
		return new SimplePageRequest(pageable.getPageNumber(), pageable.getPageSize());
	}
	
}
