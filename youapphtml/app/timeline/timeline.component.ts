import {Component} from "angular2/core"


import {AppComponent} from "../app.component";
import {GlobalService} from '../global.service';

@Component(
    {
        selector:'timeline',
        templateUrl:'app/timeline/timeline.component.html'
    }
)

export class TimelineComponent implements OnInit,AfterContentInit {


    constructor(private _globalService:GlobalService) {

    }

    ngOnInit() {

    }

    ngAfterViewInit(){
        setTimeout(() => {
            this._globalService.setTitile('Timeline','timeline');
        }, 1);

    }

}
