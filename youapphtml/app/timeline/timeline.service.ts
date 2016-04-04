import {Injectable } from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import 'rxjs/Rx';
import {Jsonp, URLSearchParams} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {GlobalService} from '../global.service'
import {CallbackObject} from "../callbackobject.component";

@Injectable()
export class TimelineService{


    private _getTimelineUrl=
        '/usermanager/getTimeline';

    constructor (private http: Http,
    private jsonp:Jsonp,
                 private _globalService:GlobalService
    ) {}

    getTimelines(_callback:CallbackObject){
        let headers = new Headers({
            'Content_Type': 'jsonp'

        });
        let options = new RequestOptions({ headers: headers });
        var params = new URLSearchParams();
        params.set('userName', this._globalService.getSessionUserName()); // the user's search value

        this._globalService.getByJsonp(
            this._globalService.getWholeUrl(this._getTimelineUrl)
            ,params,_callback);
    }


}