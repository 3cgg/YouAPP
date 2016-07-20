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
    var core_1, http_1, http_2, http_3, global_service_1;
    var UserManagerService;
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
            function (_1) {},
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            }],
        execute: function() {
            UserManagerService = (function () {
                function UserManagerService(http, jsonp, _globalService) {
                    this.http = http;
                    this.jsonp = jsonp;
                    this._globalService = _globalService;
                    this._getUsersByPageUrl = '/usermanager/getUsersByPage';
                    this._getUserByIdUrl = '/usermanager/getUserById';
                }
                UserManagerService.prototype.getUsers = function (_callback) {
                    var headers = new http_3.Headers({
                        'Content_Type': 'jsonp'
                    });
                    var options = new http_3.RequestOptions({ headers: headers });
                    this._globalService.getByJsonp(this._globalService.getWholeUrl(this._getUsersByPageUrl), {}, _callback);
                };
                UserManagerService.prototype.getUserById = function (_id, _callback) {
                    var params = new http_2.URLSearchParams();
                    params.set('id', _id); // the user's search value
                    this._globalService.getByJsonp(this._globalService.getWholeUrl(this._getUserByIdUrl), params, _callback);
                };
                UserManagerService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof http_1.Http !== 'undefined' && http_1.Http) === 'function' && _a) || Object, (typeof (_b = typeof http_2.Jsonp !== 'undefined' && http_2.Jsonp) === 'function' && _b) || Object, global_service_1.GlobalService])
                ], UserManagerService);
                return UserManagerService;
                var _a, _b;
            }());
            exports_1("UserManagerService", UserManagerService);
        }
    }
});
//# sourceMappingURL=user-manager.service.js.map