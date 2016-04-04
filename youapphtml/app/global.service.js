System.register(['angular2/core', 'angular2/http', 'rxjs/Observable', 'rxjs/Rx', './pageinfo.component'], function(exports_1, context_1) {
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
    var core_1, http_1, Observable_1, http_2, pageinfo_component_1;
    var GlobalService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
                http_2 = http_1_1;
            },
            function (Observable_1_1) {
                Observable_1 = Observable_1_1;
            },
            function (_1) {},
            function (pageinfo_component_1_1) {
                pageinfo_component_1 = pageinfo_component_1_1;
            }],
        execute: function() {
            GlobalService = (function () {
                function GlobalService(http, jsonp) {
                    this.http = http;
                    this.jsonp = jsonp;
                }
                GlobalService.prototype.getByJsonp = function (_url, _urlSearchParams, _callback) {
                    var _this = this;
                    if (!(_urlSearchParams instanceof http_2.URLSearchParams)) {
                        _urlSearchParams = new http_2.URLSearchParams();
                    }
                    _urlSearchParams.set('callback', 'JSONP_CALLBACK');
                    if (this.getTicket() && "" != this.getTicket()) {
                        _urlSearchParams.set('_youapp_ticket', this.getTicket());
                    }
                    this.jsonp.get(_url, { search: _urlSearchParams })
                        .map(function (res) { return res.json(); })
                        .do(function (data) { return console.log(data); })
                        .catch(this.handleError)
                        .retry(1)
                        .subscribe(function (data) { _this.handleSuccess(data, _callback); }, function (error) { });
                };
                GlobalService.prototype.handleError = function (error) {
                    // in a real world app, we may send the error to some remote logging infrastructure
                    // instead of just logging it to the console
                    console.error(error);
                    if (error.json()) {
                        return Observable_1.Observable.throw(error.json().data || 'Server error');
                    }
                    else {
                        return Observable_1.Observable.throw('Server error');
                    }
                };
                GlobalService.prototype.handleSuccess = function (jsonObj, _callback) {
                    if ('SUCCESS' == jsonObj.status) {
                        _callback.invoke(jsonObj.data ? jsonObj.data : jsonObj.status);
                    }
                    else if ('SUCCESS_LOGIN' == jsonObj.status) {
                        _callback.invoke(jsonObj.data ? jsonObj.data : jsonObj.status);
                    }
                    else if ('MESSAGE' == jsonObj.status) {
                        this.setSuccess(jsonObj.data ? jsonObj.data : jsonObj.status);
                    }
                    else {
                        this.setError(jsonObj.data ? jsonObj.data : jsonObj.status);
                    }
                };
                GlobalService.prototype.handleError = function (error) {
                    // in a real world app, we may send the error to some remote logging infrastructure
                    // instead of just logging it to the console
                    console.error(error);
                    if (error.json()) {
                        return Observable_1.Observable.throw(error.json().data || 'Server error');
                    }
                    else {
                        return Observable_1.Observable.throw('Server error');
                    }
                };
                GlobalService.prototype.setAppComponent = function (_appComponent) {
                    this.appComponent = _appComponent;
                };
                GlobalService.prototype.setTitile = function (_itemTile, itemTileDesc) {
                    this.appComponent.pageInfo.itemTitle = _itemTile;
                    this.appComponent.pageInfo.itemTitleDesc = itemTileDesc;
                };
                GlobalService.prototype.setSessionUser = function (_sessionUser) {
                    // this.appComponent.pageInfo.sessionUser.userName=_sessionUser.userName;
                    // this.appComponent.pageInfo.sessionUser.natureName=_sessionUser.natureName;
                    this.appComponent.pageInfo.sessionUser = _sessionUser;
                };
                GlobalService.prototype.setError = function (_error) {
                    this.appComponent.pageInfo.isError = true;
                    this.appComponent.pageInfo.errorMessage = _error;
                };
                GlobalService.prototype.setSuccess = function (_success) {
                    this.appComponent.pageInfo.isSuccess = true;
                    this.appComponent.pageInfo.successMessage = _success;
                };
                GlobalService.prototype.clearError = function () {
                    this.appComponent.pageInfo.isError = false;
                };
                GlobalService.prototype.clearSuccess = function () {
                    this.appComponent.pageInfo.isSuccess = false;
                };
                GlobalService.prototype.reset = function () {
                    this.clearError();
                    this.clearSuccess();
                };
                GlobalService.prototype.getTicket = function () {
                    return this.appComponent.pageInfo.sessionUser.ticket;
                };
                GlobalService.prototype.putTicket = function (_ticket) {
                    this.appComponent.pageInfo.sessionUser.ticket = _ticket;
                };
                GlobalService.prototype.clearCurrentSession = function () {
                    this.appComponent.pageInfo = new pageinfo_component_1.PageInfo();
                };
                GlobalService.prototype.getSessionUser = function () {
                    return this.appComponent.pageInfo.sessionUser;
                };
                GlobalService.prototype.getSessionUserName = function () {
                    var sessionUser = this.getSessionUser();
                    return sessionUser.userName;
                };
                GlobalService.prototype.getEndpoint = function () {
                    return this.appComponent.pageInfo.endpoint;
                };
                GlobalService.prototype.getWholeUrl = function (relativePath) {
                    return this.getEndpoint() + relativePath;
                };
                GlobalService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof http_1.Http !== 'undefined' && http_1.Http) === 'function' && _a) || Object, (typeof (_b = typeof http_2.Jsonp !== 'undefined' && http_2.Jsonp) === 'function' && _b) || Object])
                ], GlobalService);
                return GlobalService;
                var _a, _b;
            }());
            exports_1("GlobalService", GlobalService);
        }
    }
});
//# sourceMappingURL=global.service.js.map