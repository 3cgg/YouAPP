import {Injectable } from 'angular2/core';
import {PageInfo} from './pageinfo.component'
import {CookieService} from 'angular2-cookie/core'
import {SessionUser} from "../session-user.component";

@Injectable()
export class StoreService{

    constructor(private _cookieService:CookieService){

        console.log('e');

    }

    pageInfo:PageInfo=new PageInfo();

    getPageInfo(){
        return this.pageInfo;
    }

    private ticketKey:String='ticket';

    private setTicket(_ticket:String){
        this._cookieService.putObject(this.ticketKey, _ticket);

    }

    getTicket(){
        return this.getCookie(this.ticketKey);
    }

    private userIdKey:String='_user_id';

    setSessionInfo(_sessionUser:SessionUser){
        this._cookieService.putObject(this.userIdKey, _sessionUser.userId);
        this.setTicket(_sessionUser.ticket);
    }

    clearSessionInfo(){
        this._cookieService.remove(this.ticketKey);
        this._cookieService.remove(this.userIdKey);
        this._cookieService.removeAll();
    }


    getSessionUserId(){
        return this.getCookie(this.userIdKey);
    }

    private getCookie(_key:String){
        let val=this._cookieService.get(_key);
        if(val!=null&&val.length>0){
            val=decodeURI(val);
            if(val.charAt(0)=='"'&&val.charAt(val.length-1)=='"'){
                val=val.substring(1,val.length-1);
            }
        }
        return val;
    }

}