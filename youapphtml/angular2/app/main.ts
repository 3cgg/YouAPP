import {bootstrap}    from 'angular2/platform/browser';
import {AppComponent} from './app.component';
import {enableProdMode} from 'angular2/core';
import {StoreService} from './store.service'
import {CookieService } from 'angular2-cookie/core'

enableProdMode();

bootstrap(AppComponent,[StoreService,CookieService]);
