import {Injectable } from 'angular2/core';
import {AppComponent } from './app.component';

@Injectable()
export class GlobalService{

    appComponent:AppComponent;

    setAppComponent(_appComponent:AppComponent){
        this.appComponent=_appComponent;
    }

    setTitile(_itemTile:String,itemTileDesc:String){
        this.appComponent.itemTitle=_itemTile;
        this.appComponent.itemTitleDesc=itemTileDesc;
    }

    

}