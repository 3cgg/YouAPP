System.register(['angular2/core', 'angular2/http', 'rxjs/Rx', '../global.service'], function(exports_1, context_1) {
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
    var core_1, http_1, http_2, global_service_1;
    var LoginService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
                http_2 = http_1_1;
            },
            function (_1) {},
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            }],
        execute: function() {
            LoginService = (function () {
                function LoginService(http, jsonp, _globalService) {
                    this.http = http;
                    this.jsonp = jsonp;
                    this._globalService = _globalService;
                    this._loginUrl = '/controller.login/login';
                    this._loginoutUrl = '/controller.loginout/loginout';
                }
                LoginService.prototype.login = function (_userName, _password, _callback) {
                    var params = new http_2.URLSearchParams();
                    params.set('_name', _userName); // the user's search value
                    params.set('_password', _password);
                    this._globalService.getByJsonp(this._globalService.getWholeUrl(this._loginUrl), params, _callback);
                    //jsonp does not support custom request headers. for only test.
                    // return this.jsonp.get(this._loginUrl,{ search: params })
                    //     .map(res => res.json())
                    //     .do(data=>console.log(data))
                    //     .catch(this.handleError);
                };
                LoginService.prototype.loginout = function (_callback) {
                    var params = new http_2.URLSearchParams();
                    this._globalService.getByJsonp(this._globalService.getWholeUrl(this._loginoutUrl), params, _callback);
                    //jsonp does not support custom request headers. for only test.
                    // return this.jsonp.get(this._loginUrl,{ search: params })
                    //     .map(res => res.json())
                    //     .do(data=>console.log(data))
                    //     .catch(this.handleError);
                };
                LoginService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof http_1.Http !== 'undefined' && http_1.Http) === 'function' && _a) || Object, (typeof (_b = typeof http_2.Jsonp !== 'undefined' && http_2.Jsonp) === 'function' && _b) || Object, global_service_1.GlobalService])
                ], LoginService);
                return LoginService;
                var _a, _b;
            }());
            exports_1("LoginService", LoginService);
        }
    }
});
//# sourceMappingURL=login.service.js.map