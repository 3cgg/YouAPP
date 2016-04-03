import {Component} from "angular2/core"


import {AppComponent} from "../app.component";
import {GlobalService} from '../global.service';
import {TimelineService} from './timeline.service'
import {CallbackObject} from "../callbackobject.component";

@Component(
    {
        selector:'timeline',
        templateUrl:'app/timeline/timeline.component.html',
        providers:[
            TimelineService
        ]
    }
)

export class TimelineComponent implements OnInit,AfterContentInit {

    timelines:Object;
    

    constructor(private _globalService:GlobalService,
    private _timelineService:TimelineService
    ) {

    }

    ngOnInit() {

    }

    ngAfterViewInit(){
        setTimeout(() => {
            this._globalService.setTitile('Timeline','timeline');
        }, 1);
        this.getTimelines();
        this._globalService.reset();
    }

    getTimelines(){
        this._timelineService.getTimelines(new CallbackObject(function (data,_object) {
            _object.timelines=data;
        },this));

    }

}
