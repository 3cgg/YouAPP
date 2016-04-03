System.register(['./userinfo.component'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var userinfo_component_1;
    var PageInfo;
    return {
        setters:[
            function (userinfo_component_1_1) {
                userinfo_component_1 = userinfo_component_1_1;
            }],
        execute: function() {
            PageInfo = (function () {
                function PageInfo() {
                    this.userInfo = new userinfo_component_1.UserInfo();
                    this.itemTitle = '';
                    this.itemTitleDesc = '';
                    this.isError = false;
                    this.errorMessage = '';
                    this.isSuccess = false;
                    this.successMessage = '';
                    this.ticket = '';
                }
                return PageInfo;
            }());
            exports_1("PageInfo", PageInfo);
        }
    }
});
//# sourceMappingURL=pageinfo.component.js.map