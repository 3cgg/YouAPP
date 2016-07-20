System.register(['angular2/core', './pageinfo.component', 'angular2-cookie/core'], function(exports_1, context_1) {
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
    var core_1, pageinfo_component_1, core_2;
    var StoreService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (pageinfo_component_1_1) {
                pageinfo_component_1 = pageinfo_component_1_1;
            },
            function (core_2_1) {
                core_2 = core_2_1;
            }],
        execute: function() {
            StoreService = (function () {
                function StoreService(_cookieService) {
                    this._cookieService = _cookieService;
                    this.pageInfo = new pageinfo_component_1.PageInfo();
                    this.ticketKey = 'ticket';
                    this.userIdKey = '_user_id';
                    console.log('e');
                }
                StoreService.prototype.getPageInfo = function () {
                    return this.pageInfo;
                };
                StoreService.prototype.setTicket = function (_ticket) {
                    this._cookieService.putObject(this.ticketKey, _ticket);
                };
                StoreService.prototype.getTicket = function () {
                    return this.getCookie(this.ticketKey);
                };
                StoreService.prototype.setSessionInfo = function (_sessionUser) {
                    this._cookieService.putObject(this.userIdKey, _sessionUser.userId);
                    this.setTicket(_sessionUser.ticket);
                };
                StoreService.prototype.clearSessionInfo = function () {
                    this._cookieService.remove(this.ticketKey);
                    this._cookieService.remove(this.userIdKey);
                    this._cookieService.removeAll();
                };
                StoreService.prototype.getSessionUserId = function () {
                    return this.getCookie(this.userIdKey);
                };
                StoreService.prototype.getCookie = function (_key) {
                    var val = this._cookieService.get(_key);
                    if (val != null && val.length > 0) {
                        val = decodeURI(val);
                        if (val.charAt(0) == '"' && val.charAt(val.length - 1) == '"') {
                            val = val.substring(1, val.length - 1);
                        }
                    }
                    return val;
                };
                StoreService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof core_2.CookieService !== 'undefined' && core_2.CookieService) === 'function' && _a) || Object])
                ], StoreService);
                return StoreService;
                var _a;
            }());
            exports_1("StoreService", StoreService);
        }
    }
});
//# sourceMappingURL=store.service.js.map