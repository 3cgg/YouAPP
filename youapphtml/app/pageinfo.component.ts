import {Component} from 'angular2/core';
import {SessionUser} from './session-user.component'

export class PageInfo{

    sessionUser:SessionUser=new SessionUser();

    itemTitle='';

    itemTitleDesc='';

    isError:boolean=false;
    errorMessage:String='';

    isSuccess:boolean=false;
    successMessage:String='';

    endpoint:String='http://localhost:8689/youapp/extapi';

}