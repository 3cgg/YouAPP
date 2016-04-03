import {Component} from 'angular2/core';
import {UserInfo} from './userinfo.component'

export class PageInfo{

    userInfo:UserInfo=new UserInfo();

    itemTitle='';

    itemTitleDesc='';

    isError:boolean=false;
    errorMessage:String='';

    isSuccess:boolean=false;
    successMessage:String='';

    ticket='';
}