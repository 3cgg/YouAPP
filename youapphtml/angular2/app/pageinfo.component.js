System.register(['./session-user.component'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var session_user_component_1;
    var PageInfo;
    return {
        setters:[
            function (session_user_component_1_1) {
                session_user_component_1 = session_user_component_1_1;
            }],
        execute: function() {
            PageInfo = (function () {
                function PageInfo() {
                    this.sessionUser = new session_user_component_1.SessionUser();
                    this.itemTitle = '';
                    this.itemTitleDesc = '';
                    this.isError = false;
                    this.errorMessage = '';
                    this.isSuccess = false;
                    this.successMessage = '';
                    this.endpoint = 'http://localhost:8689/youapp/extapi';
                }
                return PageInfo;
            }());
            exports_1("PageInfo", PageInfo);
        }
    }
});
//# sourceMappingURL=pageinfo.component.js.map