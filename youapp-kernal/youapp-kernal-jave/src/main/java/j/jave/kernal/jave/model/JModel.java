package j.jave.kernal.jave.model;

import j.jave.kernal.jave.support.databind.proext.JPropertyExtendable;
import j.jave.kernal.jave.support.validate.JValidatable;

import java.io.Serializable;

/**
 * indicate all implementations can be serialized.
 *  its strongly recommended any implementation must involve primary key. 
 * @author Administrator
 *
 */
public interface JModel extends Serializable, Cloneable ,
JValidatable,JPropertyExtendable{

}
