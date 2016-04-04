import {Component} from "angular2/core"
import {OnInit} from 'angular2/core';
import {Router} from 'angular2/router';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';

import {GlobalService} from '../global.service';
import {LoginService} from './login.service';
import {CallbackObject} from "../callbackobject.component";
import {SessionUser} from "../session-user.component";

@Component(
    {
        selector:'my-content',
        templateUrl:'app/login/login.component.html',
        providers:[
            LoginService
        ]
    }
)

export class LoginComponnet implements OnInit {

    userName:String;
    password:String;

    constructor(private _globalService:GlobalService,
    private _loginService:LoginService,
                private _router: Router
    ) {

    }

    ngOnInit() {

        System.import('app/login/login.script.js')
            .then(refToLoadedScript => {
            refToLoadedScript.initLoginComponent();
    }
            );

    }

    ngAfterViewInit(){
        setTimeout(() => {
            this._globalService.setTitile('Login','login');
        }, 1);
        this._globalService.reset();
    }

    onsubmit(){
        this.login();
    }

    errorMessage:String;

    login(){

        this._loginService.login(this.userName,this.password,
        new CallbackObject(function (data,_object) {
            let _sessionUser:SessionUser=new SessionUser();
            _sessionUser.userName=data.userName;
            _sessionUser.natureName=data.userName;
            _sessionUser.userId=data.id;
            _sessionUser.ticket=data.ticket;
            _object._globalService.setSessionUser(_sessionUser);
            _object._router.navigate(['Timeline', { }]  );
        },this)
        );


    }






    
}