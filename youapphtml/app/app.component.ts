import {Component} from 'angular2/core';
import {OnInit} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import {Observable}     from 'rxjs/Observable';
import {HTTP_PROVIDERS}    from 'angular2/http';
import {Jsonp, URLSearchParams,JSONP_PROVIDERS} from 'angular2/http';
import { RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import {Router} from 'angular2/router';
import {NewsComponent} from './news/news.component';
import {TimelineComponent} from './timeline/timeline.component';
import {GlobalService} from './global.service';
import {UserManagerComponent} from "./usermanager/usermanager.component";
import {PageInfo} from './pageinfo.component'
import {UserManagerService} from './usermanager/user-manager.service'
import {SessionUser} from './session-user.component'
import {LoginComponnet} from './login/login.component'
import {CallbackObject} from './callbackobject.component'
import {LoginService} from "./login/login.service";
import {StoreService} from './store.service'
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
        UserManagerService,
        LoginService
    ]
})

@RouteConfig([
    {
        path: '/news',
        name: 'News',
        component: NewsComponent,
        useAsDefault: true
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
    private _userManagerService:UserManagerService,
                private _router:Router,
                private _loginService:LoginService,
                private _storeService:StoreService
    ) {
        
    }

    globalDateFormat='MM/dd/yy';

    pageInfo:PageInfo;

    ngOnInit() {
        System.import('dist/js/app.min.js');
        System.import('plugins/iCheck/icheck.min.js');
        this.setPageInfo();
        this._globalService.setAppComponent(this);
    }
    
    setSessionUserInfo(){
        let _ticket=this._storeService.getTicket();
        if(_ticket!=null){
            this.pageInfo.sessionUser.ticket=_ticket;
        }
        let userId=this._storeService.getSessionUserId();
        if(userId!=null){
            this._userManagerService.getUserById(userId,
                new CallbackObject(function (data,_object) {
                    _object.pageInfo.sessionUser.userName=data.userName;
                    _object.pageInfo.sessionUser.natureName=data.userName;
                },this)
            )
        }
        
    }
    
    setPageInfo(){
        this.pageInfo=this._storeService.getPageInfo();
        this.pageInfo.sessionUser=new SessionUser();
    }

    ngAfterViewInit(){
        this._globalService.reset();
        this.setSessionUserInfo();
    }

    
    closeErrorMessage(){
        this._globalService.clearError();
    }

    closeSuccessMessage(){
        this._globalService.clearSuccess();
    }
    
    loginout(){
        
        this._loginService.loginout(new CallbackObject(function (data,_object) {
            _object._globalService.clearCurrentSession();
            _object._router.navigate(['News']);
        },this))
        
    }

}


