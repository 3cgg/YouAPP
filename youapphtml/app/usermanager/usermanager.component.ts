import {Component} from "angular2/core"
import {OnInit} from 'angular2/core';
import {UserManagerService} from './user-manager.service'

import {AppComponent} from '../app.component'
import {GlobalService} from '../global.service';
import {CallbackObject} from '../callbackobject.component'
@Component(
    {
        selector:'timeline',
        templateUrl:'app/usermanager/usermanager-list.component.html',
        providers:[
            UserManagerService
        ]
    }
)

export class UserManagerComponent implements OnInit {

    users :Object=null;
    errorMessage:Object=null;

    constructor(private _globalService:GlobalService,
        private _userManagerService:UserManagerService
    ) { }

    ngAfterViewInit(){

        setTimeout(() => {
            this._globalService.setTitile('User Manager','user-manager');
        }, 1);

        this.getUsers();
        this._globalService.reset();
    }

    getUsers(){
        this._userManagerService.getUsers(new CallbackObject(function (data,_object) {
            _object.users=data.content;
        },this));
    }


}