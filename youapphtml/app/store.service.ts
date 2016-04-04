import {Injectable } from 'angular2/core';
import {PageInfo} from './pageinfo.component'

@Injectable()
export class StoreService{

    constructor(){

        console.log('e');

    }

    pageInfo:PageInfo=new PageInfo();

    getPageInfo(){
        return this.pageInfo;
    }



}