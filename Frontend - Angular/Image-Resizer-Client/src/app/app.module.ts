import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DragDropDirective } from './Utilities/drag-drop.directive';
import { HomepageComponent } from './Components/homepage/homepage.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastComponent } from './Components/toast/toast.component';
import { ShowImageComponent } from './Components/show-image/show-image.component';


@NgModule({
  declarations: [
    AppComponent,
    DragDropDirective,
    HomepageComponent,
    ToastComponent,
    ShowImageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
