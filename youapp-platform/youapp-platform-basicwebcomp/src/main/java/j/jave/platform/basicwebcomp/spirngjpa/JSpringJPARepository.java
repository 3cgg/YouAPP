package j.jave.platform.basicwebcomp.spirngjpa;

import j.jave.kernal.jave.model.JBaseModel;
import j.jave.kernal.jave.persist.JIPersist;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface JSpringJPARepository<T,ID extends Serializable> 
	extends PagingAndSortingRepository<T, Serializable>,JIPersist<JSpringJPARepository<T,ID>, JBaseModel> {

}
