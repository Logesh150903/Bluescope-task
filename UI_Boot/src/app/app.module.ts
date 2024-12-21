import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { CreateComponent } from './Components/create/create.component';
import { ListComponent } from './Components/list/list.component';
import { DetailsComponent } from './Components/details/details.component';
import { SidebarComponent } from './Components/sidebar/sidebar.component';
import { CreateformjsonComponent } from './Components/createformjson/createformjson.component';
import { MainpageComponent } from './Components/mainpage/mainpage.component';
import { ListformComponent } from './Components/listform/listform.component';
import { DetailformComponent } from './Components/detailform/detailform.component';


@NgModule({
  declarations: [
    AppComponent,
    CreateComponent,
    ListComponent,
    DetailsComponent,
    SidebarComponent,
    CreateformjsonComponent,
    MainpageComponent,
    ListformComponent,
    DetailformComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
