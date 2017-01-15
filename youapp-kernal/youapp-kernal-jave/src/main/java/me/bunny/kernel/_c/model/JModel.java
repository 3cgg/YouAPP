package me.bunny.kernel._c.model;

import java.io.Serializable;

import me.bunny.kernel._c.support.databind.proext.JPropertyExtendable;
import me.bunny.kernel._c.support.validate.JValidatable;

/**
 * indicate all implementations can be serialized.
 *  its strongly recommended any implementation must involve primary key. 
 * @author Administrator
 *
 */
public interface JModel extends Serializable, Cloneable ,
JValidatable,JPropertyExtendable{

}
