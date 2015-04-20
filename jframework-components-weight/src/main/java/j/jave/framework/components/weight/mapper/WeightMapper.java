/**
 * 
 */
package j.jave.framework.components.weight.mapper;

import j.jave.framework.components.weight.model.Weight;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="WeightMapper")
@JModelMapper(component="WeightMapper",name=Weight.class)
public interface WeightMapper extends JMapper<Weight> {

	public List<Weight> getWeightByName(String userName) ;
	
	public List<Weight> getWeightsByPage(Weight weight) ;
	
	
}
