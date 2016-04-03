import {Injectable } from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import 'rxjs/Rx';
import {Jsonp, URLSearchParams} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {GlobalService} from '../global.service'
import {CallbackObject} from "../callbackobject.component";

@Injectable()
export class LoginService{

    private _loginUrl=
        'http://localhost:8689/youapp/extapi/controller.login/login';

    constructor (private http: Http,
    private jsonp:Jsonp,
    private _globalService:GlobalService
    ) {}

    login(_userName:String,_password:String,_callback:CallbackObject){

        var params = new URLSearchParams();
        params.set('_name', _userName); // the user's search value
        params.set('_password', _password);

        this._globalService.getByJsonp(this._loginUrl,params,_callback);

        //jsonp does not support custom request headers. for only test.
        // return this.jsonp.get(this._loginUrl,{ search: params })
        //     .map(res => res.json())
        //     .do(data=>console.log(data))
        //     .catch(this.handleError);
    }



}