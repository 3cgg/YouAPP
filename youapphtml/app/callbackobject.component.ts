import {Component} from 'angular2/core';

export class CallbackObject{

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