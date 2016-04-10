System.register(['angular2/platform/browser', './app.component', 'angular2/core', './store.service', 'angular2-cookie/core'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var browser_1, app_component_1, core_1, store_service_1, core_2;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (app_component_1_1) {
                app_component_1 = app_component_1_1;
            },
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (store_service_1_1) {
                store_service_1 = store_service_1_1;
            },
            function (core_2_1) {
                core_2 = core_2_1;
            }],
        execute: function() {
            core_1.enableProdMode();
            browser_1.bootstrap(app_component_1.AppComponent, [store_service_1.StoreService, core_2.CookieService]);
        }
    }
});
//# sourceMappingURL=main.js.map