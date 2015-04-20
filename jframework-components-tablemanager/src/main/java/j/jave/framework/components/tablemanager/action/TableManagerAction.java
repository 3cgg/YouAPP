package j.jave.framework.components.tablemanager.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import j.jave.framework.components.web.jsp.JSPAction;

@Controller(value="tablemanager.tablemanageraction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableManagerAction extends JSPAction {

}
