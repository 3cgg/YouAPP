import {Component} from "angular2/core"
import {OnInit} from 'angular2/core';

import {AppComponent} from '../app.component'
import {GlobalService} from '../global.service';

@Component(
    {
        selector:'my-content',
        templateUrl:'app/news/news.component.html'
    }
)

export class NewsComponent implements OnInit {

    constructor(private _globalService:GlobalService) {
        
    }

    ngAfterViewInit(){
        setTimeout(() => {
            this._globalService.setTitile('News','news');
        }, 1);

    }
    
}