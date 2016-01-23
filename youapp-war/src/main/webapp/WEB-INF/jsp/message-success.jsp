<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${successAlertMessage!=null}">
<div  id="youapp-alert-success" class="alert alert-success alert-dismissable" >
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <span >${successAlertMessage }</span>
                  </div>
</c:if>