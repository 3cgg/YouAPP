import {Injectable } from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import 'rxjs/Rx';
import {Jsonp, URLSearchParams} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {GlobalService} from '../global.service'
import {CallbackObject} from "../callbackobject.component";

@Injectable()
export class UserManagerService{

    private _getUsersByPageUrl=
        '/usermanager/getUsersByPage';

    private _getUserByIdUrl=
        '/usermanager/getUserById';


    constructor (private http: Http,
    private jsonp:Jsonp,
                 private _globalService:GlobalService
    ) {}

    getUsers(_callback:Object){
        let headers = new Headers({
            'Content_Type': 'jsonp'

        });
        let options = new RequestOptions({ headers: headers });

        this._globalService.getByJsonp(
            this._globalService.getWholeUrl(this._getUsersByPageUrl)
            ,{},_callback);

    }



    getUserById(_id:String,_callback:CallbackObject){
        
        var params = new URLSearchParams();
        params.set('id', _id); // the user's search value

        this._globalService.getByJsonp(
            this._globalService.getWholeUrl(this._getUserByIdUrl)
            ,params,_callback);

    }

}