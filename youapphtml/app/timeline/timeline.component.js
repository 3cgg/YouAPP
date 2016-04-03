System.register(["angular2/core", '../global.service', './timeline.service', "../callbackobject.component"], function(exports_1, context_1) {
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
    var core_1, global_service_1, timeline_service_1, callbackobject_component_1;
    var TimelineComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (global_service_1_1) {
                global_service_1 = global_service_1_1;
            },
            function (timeline_service_1_1) {
                timeline_service_1 = timeline_service_1_1;
            },
            function (callbackobject_component_1_1) {
                callbackobject_component_1 = callbackobject_component_1_1;
            }],
        execute: function() {
            TimelineComponent = (function () {
                function TimelineComponent(_globalService, _timelineService) {
                    this._globalService = _globalService;
                    this._timelineService = _timelineService;
                }
                TimelineComponent.prototype.ngOnInit = function () {
                };
                TimelineComponent.prototype.ngAfterViewInit = function () {
                    var _this = this;
                    setTimeout(function () {
                        _this._globalService.setTitile('Timeline', 'timeline');
                    }, 1);
                    this.getTimelines();
                    this._globalService.reset();
                };
                TimelineComponent.prototype.getTimelines = function () {
                    this._timelineService.getTimelines(new callbackobject_component_1.CallbackObject(function (data, _object) {
                        _object.timelines = data;
                    }, this));
                };
                TimelineComponent = __decorate([
                    core_1.Component({
                        selector: 'timeline',
                        templateUrl: 'app/timeline/timeline.component.html',
                        providers: [
                            timeline_service_1.TimelineService
                        ]
                    }), 
                    __metadata('design:paramtypes', [global_service_1.GlobalService, timeline_service_1.TimelineService])
                ], TimelineComponent);
                return TimelineComponent;
            }());
            exports_1("TimelineComponent", TimelineComponent);
        }
    }
});
//# sourceMappingURL=timeline.component.js.map