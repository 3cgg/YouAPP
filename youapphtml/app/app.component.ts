import {Component} from 'angular2/core';
import {OnInit} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import {HTTP_PROVIDERS}    from 'angular2/http';
import {Jsonp, URLSearchParams,JSONP_PROVIDERS} from 'angular2/http';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import {NewsComponent} from './news/news.component';
import {TimelineComponent} from './timeline/timeline.component';
import {GlobalService} from './global.service';
import {UserManagerComponent} from "./usermanager/usermanager.component";
import {PageInfo} from './pageinfo.component'
import {UserManagerService} from './usermanager/user-manager.service'
import {UserInfo} from './userinfo.component'
import {LoginComponnet} from './login/login.component'
import {CallbackObject} from './callbackobject.component'
@Component({
    selector: 'app-html',
    templateUrl:'app/app.component.html',
    directives: [ROUTER_DIRECTIVES],
    providers: [
        ROUTER_PROVIDERS,
        GlobalService,
        Http,
        HTTP_PROVIDERS,
        JSONP_PROVIDERS,
        UserManagerService
    ]
})

@RouteConfig([
    {
        path: '/news',
        name: 'News',
        component: NewsComponent
        // useAsDefault: true
    },
    {
        path: '/timeline',
        name: 'Timeline',
        component: TimelineComponent
    },
    {
        path: '/usermanager',
        name: 'UserManager',
        component: UserManagerComponent
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginComponnet
    }
])

export class AppComponent implements OnInit {

    constructor(private _globalService:GlobalService,
    private _userManagerService:UserManagerService
    ) {

    }

    globalDateFormat='MM/dd/yy';

    pageInfo:PageInfo=new PageInfo();

    ngOnInit() {
        System.import('dist/js/app.min.js');
        System.import('plugins/iCheck/icheck.min.js');
        this._globalService.setAppComponent(this);
        this._globalService.setTitile('Init','init');
        this.getUser();
    }

    private getUser(){
        this._userManagerService.getUserById('bd2713e6ad5d493ab2e25c34f6cd339a',
            
            new CallbackObject(function (data,_object) {
                _object.pageInfo.userInfo.userName=data.id;
                _object.pageInfo.userInfo.natureName=data.version;
            },this)
            
            );
    }
    
    closeErrorMessage(){
        this._globalService.clearError();
    }

    closeSuccessMessage(){
        this._globalService.clearSuccess();
    }
}


