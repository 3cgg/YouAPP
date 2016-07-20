import {Component} from 'angular2/core';

export class CallbackObject{

    /**
     *
     * @param _callback like function (data,_object) {} the first argument is the data returned from the service,
     * the second parameter is the object reference to the @param
     * @param _object : generally it is 'this'
     */
    constructor(_callback:Function,
    _object:Object){
        this._callback=_callback;
        this._object=_object;
    }

    _callback:Function;

    _object:Object;

    public invoke(data:Object){
        this._callback(data,this._object);
    }

}