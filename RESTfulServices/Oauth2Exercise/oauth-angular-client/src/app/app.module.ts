import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule }   from '@angular/router';

import { AppComponent } from './app.component';
import { WidgetPageComponent } from './widget-page/widget-page.component';
import { HomePageComponent } from './home-page/home-page.component';

@NgModule({
  declarations: [
    AppComponent,
    WidgetPageComponent,
    HomePageComponent    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomePageComponent, pathMatch: 'full' }
    ], {
      onSameUrlNavigation: 'reload'
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
