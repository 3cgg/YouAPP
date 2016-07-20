System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var CallbackObject;
    return {
        setters:[],
        execute: function() {
            CallbackObject = (function () {
                /**
                 *
                 * @param _callback like function (data,_object) {} the first argument is the data returned from the service,
                 * the second parameter is the object reference to the @param
                 * @param _object : generally it is 'this'
                 */
                function CallbackObject(_callback, _object) {
                    this._callback = _callback;
                    this._object = _object;
                }
                CallbackObject.prototype.invoke = function (data) {
                    this._callback(data, this._object);
                };
                return CallbackObject;
            }());
            exports_1("CallbackObject", CallbackObject);
        }
    }
});
//# sourceMappingURL=callbackobject.component.js.map