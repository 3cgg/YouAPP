import {Injectable } from 'angular2/core';
import {AppComponent } from './app.component';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import 'rxjs/Rx';
import {Jsonp, URLSearchParams} from 'angular2/http';
import {Headers, RequestOptions} from 'angular2/http';
import {PageInfo} from './pageinfo.component'
import {SessionUser} from "./session-user.component";

@Injectable()
export class GlobalService{

    appComponent:AppComponent;

    constructor(private http: Http,
                private jsonp:Jsonp){}
    
    getByJsonp(_url:String,_urlSearchParams:URLSearchParams,_callback :Object){

        if(!(_urlSearchParams instanceof  URLSearchParams)){
            _urlSearchParams=new URLSearchParams();
        }
        _urlSearchParams.set('callback', 'JSONP_CALLBACK');

        if(this.getTicket()&&""!=this.getTicket()){
            _urlSearchParams.set('_youapp_ticket', this.getTicket());
        }

        this.jsonp.get(_url,{ search: _urlSearchParams })
            .map(res =>res.json())
            .do(data=>console.log(data))
            .catch(this.handleError)
            .retry(1)
            .subscribe(
                data=> {this.handleSuccess(data,_callback)},
                error=>{}
            );
        
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

    private handleSuccess (jsonObj: Object,_callback :Object){

        if('SUCCESS'==jsonObj.status){
            _callback.invoke(jsonObj.data?jsonObj.data:jsonObj.status);
            // _callback._callback(jsonObj.data,_callback._object);
        }
        else if('SUCCESS_LOGIN'==jsonObj.status){
            _callback.invoke(jsonObj.data?jsonObj.data:jsonObj.status);
        }
        else if('MESSAGE'==jsonObj.status){
            this.setSuccess(jsonObj.data?jsonObj.data:jsonObj.status);
        }
        else{
            this.setError(jsonObj.data?jsonObj.data:jsonObj.status);
        }

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
    
    setAppComponent(_appComponent:AppComponent){
        this.appComponent=_appComponent;
    }

    setTitile(_itemTile:String,itemTileDesc:String){
        this.appComponent.pageInfo.itemTitle=_itemTile;
        this.appComponent.pageInfo.itemTitleDesc=itemTileDesc;
    }

    setSessionUser(_sessionUser:SessionUser){
        // this.appComponent.pageInfo.sessionUser.userName=_sessionUser.userName;
        // this.appComponent.pageInfo.sessionUser.natureName=_sessionUser.natureName;
        this.appComponent.pageInfo.sessionUser=_sessionUser;
    }
    
    setError(_error:String){
        this.appComponent.pageInfo.isError=true;
        this.appComponent.pageInfo.errorMessage=_error;
    }

    setSuccess(_success:String){
        this.appComponent.pageInfo.isSuccess=true;
        this.appComponent.pageInfo.successMessage=_success;
    }

    clearError(){
        this.appComponent.pageInfo.isError=false;
    }

    clearSuccess(){
        this.appComponent.pageInfo.isSuccess=false;
    }

    reset(){
        this.clearError();
        this.clearSuccess();
    }

    getTicket(){
        return this.appComponent.pageInfo.sessionUser.ticket;
    }

    putTicket(_ticket:String){
        this.appComponent.pageInfo.sessionUser.ticket=_ticket;
    }


    clearCurrentSession(){
        this.appComponent.pageInfo=new PageInfo();
    }

    getSessionUser(){
        return this.appComponent.pageInfo.sessionUser;
    }

    getSessionUserName(){
        let sessionUser:SessionUser =this.getSessionUser();
        return sessionUser.userName;
    }

    getEndpoint(){
        return this.appComponent.pageInfo.endpoint;
    }

    getWholeUrl(relativePath:String){
        return this.getEndpoint()+relativePath;
    }


}