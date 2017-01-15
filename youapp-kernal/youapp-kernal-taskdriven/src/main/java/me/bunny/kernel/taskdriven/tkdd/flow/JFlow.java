package me.bunny.kernel.taskdriven.tkdd.flow;

import me.bunny.kernel.taskdriven.tkdd.JNotifyStart;
import me.bunny.kernel.taskdriven.tkdd.JTask;

/**
 * as a part of the whole job, consists of one or more {@link JTask} units, 
 * also make a role as the super class for all flows such as flow groups.
 * @author J
 *
 */
public interface JFlow extends JNotifyStart {

	public void put(JTask task);

}
