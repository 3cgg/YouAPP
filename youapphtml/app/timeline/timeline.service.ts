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
        'http://localhost:8689/youapp/extapi/usermanager/getTimeline';

    constructor (private http: Http,
    private jsonp:Jsonp,
                 private _globalService:GlobalService
    ) {}

    getTimelines(_callback:CallbackObject){
        let headers = new Headers({
            'Content_Type': 'jsonp'

        });
        let options = new RequestOptions({ headers: headers });

        this._globalService.getByJsonp(this._getTimelineUrl,{},_callback);
    }


}