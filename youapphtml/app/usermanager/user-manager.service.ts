import {Injectable } from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import 'rxjs/Rx';
import {Jsonp, URLSearchParams} from 'angular2/http';

@Injectable()
export class UserManagerService{

    private _getUsersByPage=
        'http://localhost:8689/youapp/extapi/usermanager/getUsersByPage?callback=JSONP_CALLBACK';

    constructor (private http: Http,
    private jsonp:Jsonp
    ) {}

    getUsers(){

        return this.jsonp.get(this._getUsersByPage)
            .map(res => res.json().data)
            .do(data=>console.log(data))
            .catch(this.handleError);

    }

    private handleError (error: Response) {
        // in a real world app, we may send the error to some remote logging infrastructure
        // instead of just logging it to the console
        console.error(error);
        if(error.json()){
            return Observable.throw(error.json().data || 'Server error');
        }
        else{
            return Observable.throw('Server error');
        }
    }

}