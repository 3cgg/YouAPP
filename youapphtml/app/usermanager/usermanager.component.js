System.register(["angular2/core", './user-manager.service', '../global.service', '../callbackobject.component'], function(exports_1, context_1) {
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
    var core_1, user_manager_service_1, global_service_1, callbackobject_component_1;
    var UserManagerComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (user_manager_service_1_1) {
                user_manager_service_1 = user_manager_service_1_1;
            },
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            },
            function (callbackobject_component_1_1) {
                callbackobject_component_1 = callbackobject_component_1_1;
            }],
        execute: function() {
            UserManagerComponent = (function () {
                function UserManagerComponent(_globalService, _userManagerService) {
                    this._globalService = _globalService;
                    this._userManagerService = _userManagerService;
                    this.users = null;
                    this.errorMessage = null;
                }
                UserManagerComponent.prototype.ngAfterViewInit = function () {
                    var _this = this;
                    setTimeout(function () {
                        _this._globalService.setTitile('User Manager', 'user-manager');
                    }, 1);
                    this.getUsers();
                    this._globalService.reset();
                };
                UserManagerComponent.prototype.getUsers = function () {
                    this._userManagerService.getUsers(new callbackobject_component_1.CallbackObject(function (data, _object) {
                        _object.users = data.content;
                    }, this));
                };
                UserManagerComponent = __decorate([
                    core_1.Component({
                        selector: 'timeline',
                        templateUrl: 'app/usermanager/usermanager-list.component.html',
                        providers: [
                            user_manager_service_1.UserManagerService
                        ]
                    }), 
                    __metadata('design:paramtypes', [global_service_1.GlobalService, user_manager_service_1.UserManagerService])
                ], UserManagerComponent);
                return UserManagerComponent;
            }());
            exports_1("UserManagerComponent", UserManagerComponent);
        }
    }
});
//# sourceMappingURL=usermanager.component.js.map