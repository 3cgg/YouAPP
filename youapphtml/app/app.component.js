System.register(['angular2/core', 'angular2/http', 'angular2/router', './news/news.component', './timeline/timeline.component', './global.service', "./usermanager/usermanager.component", './usermanager/user-manager.service', './login/login.component', './callbackobject.component', "./login/login.service", './store.service'], function(exports_1, context_1) {
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
    var core_1, http_1, http_2, http_3, router_1, router_2, news_component_1, timeline_component_1, global_service_1, usermanager_component_1, user_manager_service_1, login_component_1, callbackobject_component_1, login_service_1, store_service_1;
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
                router_2 = router_1_1;
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
            function (user_manager_service_1_1) {
                user_manager_service_1 = user_manager_service_1_1;
            },
            function (login_component_1_1) {
                login_component_1 = login_component_1_1;
            },
            function (callbackobject_component_1_1) {
                callbackobject_component_1 = callbackobject_component_1_1;
            },
            function (login_service_1_1) {
                login_service_1 = login_service_1_1;
            },
            function (store_service_1_1) {
                store_service_1 = store_service_1_1;
            }],
        execute: function() {
            AppComponent = (function () {
                function AppComponent(_globalService, _userManagerService, _router, _loginService, _storeService) {
                    this._globalService = _globalService;
                    this._userManagerService = _userManagerService;
                    this._router = _router;
                    this._loginService = _loginService;
                    this._storeService = _storeService;
                    this.globalDateFormat = 'MM/dd/yy';
                }
                AppComponent.prototype.ngOnInit = function () {
                    System.import('dist/js/app.min.js');
                    System.import('plugins/iCheck/icheck.min.js');
                    this.setPageInfo();
                    this._globalService.setAppComponent(this);
                };
                AppComponent.prototype.setPageInfo = function () {
                    this.pageInfo = this._storeService.getPageInfo();
                };
                AppComponent.prototype.ngAfterViewInit = function () {
                    this._globalService.reset();
                    this.getUser();
                };
                AppComponent.prototype.getUser = function () {
                    this._userManagerService.getUserById(this.pageInfo.sessionUser.userId, new callbackobject_component_1.CallbackObject(function (data, _object) {
                        _object.pageInfo.sessionUser.userName = data.userName;
                        _object.pageInfo.sessionUser.natureName = data.userName;
                    }, this));
                };
                AppComponent.prototype.closeErrorMessage = function () {
                    this._globalService.clearError();
                };
                AppComponent.prototype.closeSuccessMessage = function () {
                    this._globalService.clearSuccess();
                };
                AppComponent.prototype.loginout = function () {
                    this._loginService.loginout(new callbackobject_component_1.CallbackObject(function (data, _object) {
                        _object._globalService.clearCurrentSession();
                        _object._router.navigate(['News']);
                    }, this));
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
                            user_manager_service_1.UserManagerService,
                            login_service_1.LoginService
                        ]
                    }),
                    router_1.RouteConfig([
                        {
                            path: '/news',
                            name: 'News',
                            component: news_component_1.NewsComponent,
                            useAsDefault: true
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
                    __metadata('design:paramtypes', [global_service_1.GlobalService, user_manager_service_1.UserManagerService, (typeof (_a = typeof router_2.Router !== 'undefined' && router_2.Router) === 'function' && _a) || Object, login_service_1.LoginService, store_service_1.StoreService])
                ], AppComponent);
                return AppComponent;
                var _a;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map