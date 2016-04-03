System.register(['angular2/core', 'angular2/http', 'angular2/router', './news/news.component', './timeline/timeline.component', './global.service', "./usermanager/usermanager.component", './pageinfo.component', './usermanager/user-manager.service', './login/login.component', './callbackobject.component'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, http_2, http_3, router_1, news_component_1, timeline_component_1, global_service_1, usermanager_component_1, pageinfo_component_1, user_manager_service_1, login_component_1, callbackobject_component_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
                http_2 = http_1_1;
                http_3 = http_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (news_component_1_1) {
                news_component_1 = news_component_1_1;
            },
            function (timeline_component_1_1) {
                timeline_component_1 = timeline_component_1_1;
            },
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            },
            function (usermanager_component_1_1) {
                usermanager_component_1 = usermanager_component_1_1;
            },
            function (pageinfo_component_1_1) {
                pageinfo_component_1 = pageinfo_component_1_1;
            },
            function (user_manager_service_1_1) {
                user_manager_service_1 = user_manager_service_1_1;
            },
            function (login_component_1_1) {
                login_component_1 = login_component_1_1;
            },
            function (callbackobject_component_1_1) {
                callbackobject_component_1 = callbackobject_component_1_1;
            }],
        execute: function() {
            AppComponent = (function () {
                function AppComponent(_globalService, _userManagerService) {
                    this._globalService = _globalService;
                    this._userManagerService = _userManagerService;
                    this.globalDateFormat = 'MM/dd/yy';
                    this.pageInfo = new pageinfo_component_1.PageInfo();
                }
                AppComponent.prototype.ngOnInit = function () {
                    System.import('dist/js/app.min.js');
                    System.import('plugins/iCheck/icheck.min.js');
                    this._globalService.setAppComponent(this);
                    this._globalService.setTitile('Init', 'init');
                    this.getUser();
                };
                AppComponent.prototype.getUser = function () {
                    this._userManagerService.getUserById('bd2713e6ad5d493ab2e25c34f6cd339a', new callbackobject_component_1.CallbackObject(function (data, _object) {
                        _object.pageInfo.userInfo.userName = data.id;
                        _object.pageInfo.userInfo.natureName = data.version;
                    }, this));
                };
                AppComponent.prototype.closeErrorMessage = function () {
                    this._globalService.clearError();
                };
                AppComponent.prototype.closeSuccessMessage = function () {
                    this._globalService.clearSuccess();
                };
                AppComponent = __decorate([
                    core_1.Component({
                        selector: 'app-html',
                        templateUrl: 'app/app.component.html',
                        directives: [router_1.ROUTER_DIRECTIVES],
                        providers: [
                            router_1.ROUTER_PROVIDERS,
                            global_service_1.GlobalService,
                            http_1.Http,
                            http_2.HTTP_PROVIDERS,
                            http_3.JSONP_PROVIDERS,
                            user_manager_service_1.UserManagerService
                        ]
                    }),
                    router_1.RouteConfig([
                        {
                            path: '/news',
                            name: 'News',
                            component: news_component_1.NewsComponent
                        },
                        {
                            path: '/timeline',
                            name: 'Timeline',
                            component: timeline_component_1.TimelineComponent
                        },
                        {
                            path: '/usermanager',
                            name: 'UserManager',
                            component: usermanager_component_1.UserManagerComponent
                        },
                        {
                            path: '/login',
                            name: 'Login',
                            component: login_component_1.LoginComponnet
                        }
                    ]), 
                    __metadata('design:paramtypes', [global_service_1.GlobalService, user_manager_service_1.UserManagerService])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map