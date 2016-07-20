System.register(["angular2/core", 'angular2/router', '../global.service', './login.service', "../callbackobject.component", "../session-user.component", '../store.service'], function(exports_1, context_1) {
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
    var core_1, router_1, global_service_1, login_service_1, callbackobject_component_1, session_user_component_1, store_service_1;
    var LoginComponnet;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            },
            function (login_service_1_1) {
                login_service_1 = login_service_1_1;
            },
            function (callbackobject_component_1_1) {
                callbackobject_component_1 = callbackobject_component_1_1;
            },
            function (session_user_component_1_1) {
                session_user_component_1 = session_user_component_1_1;
            },
            function (store_service_1_1) {
                store_service_1 = store_service_1_1;
            }],
        execute: function() {
            LoginComponnet = (function () {
                function LoginComponnet(_globalService, _loginService, _router, _storeService) {
                    this._globalService = _globalService;
                    this._loginService = _loginService;
                    this._router = _router;
                    this._storeService = _storeService;
                }
                LoginComponnet.prototype.ngOnInit = function () {
                    System.import('app/login/login.script.js')
                        .then(function (refToLoadedScript) {
                        refToLoadedScript.initLoginComponent();
                    });
                };
                LoginComponnet.prototype.ngAfterViewInit = function () {
                    var _this = this;
                    setTimeout(function () {
                        _this._globalService.setTitile('Login', 'login');
                    }, 1);
                    this._globalService.reset();
                };
                LoginComponnet.prototype.onsubmit = function () {
                    this.login();
                };
                LoginComponnet.prototype.login = function () {
                    this._loginService.login(this.userName, this.password, new callbackobject_component_1.CallbackObject(function (data, _object) {
                        var _sessionUser = new session_user_component_1.SessionUser();
                        _sessionUser.userName = data.userName;
                        _sessionUser.natureName = data.userName;
                        _sessionUser.userId = data.userId;
                        _sessionUser.ticket = data.ticket;
                        _object._globalService.setSessionUser(_sessionUser);
                        _object._router.navigate(['Timeline', {}]);
                    }, this));
                };
                LoginComponnet = __decorate([
                    core_1.Component({
                        selector: 'my-content',
                        templateUrl: 'app/login/login.component.html',
                        providers: [
                            login_service_1.LoginService
                        ]
                    }), 
                    __metadata('design:paramtypes', [global_service_1.GlobalService, login_service_1.LoginService, (typeof (_a = typeof router_1.Router !== 'undefined' && router_1.Router) === 'function' && _a) || Object, store_service_1.StoreService])
                ], LoginComponnet);
                return LoginComponnet;
                var _a;
            }());
            exports_1("LoginComponnet", LoginComponnet);
        }
    }
});
//# sourceMappingURL=login.component.js.map