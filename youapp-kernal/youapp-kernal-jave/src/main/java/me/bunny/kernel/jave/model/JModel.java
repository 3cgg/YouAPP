package me.bunny.kernel.jave.model;

import java.io.Serializable;

import me.bunny.kernel.jave.support.databind.proext.JPropertyExtendable;
import me.bunny.kernel.jave.support.validate.JValidatable;

/**
 * indicate all implementations can be serialized.
 *  its strongly recommended any implementation must involve primary key. 
 * @author Administrator
 *
 */
public interface JModel extends Serializable, Cloneable ,
JValidatable,JPropertyExtendable{

}
