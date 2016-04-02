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

@Component({
    selector: 'app-html',
    templateUrl:'app/app.component.html',
    directives: [ROUTER_DIRECTIVES],
    providers: [
        ROUTER_PROVIDERS,
        GlobalService,
        Http,
        HTTP_PROVIDERS,
        JSONP_PROVIDERS
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
    }
])

export class AppComponent implements OnInit {

    constructor(private _globalService:GlobalService) {

    }

    itemTitle='dd';

    itemTitleDesc='dd-desc';

    ngOnInit() {
        System.import('dist/js/app.min.js');
        this._globalService.setAppComponent(this);
        this._globalService.setTitile('APP','app-app');
    }

    globalDateFormat='MM/dd/yy';

}


