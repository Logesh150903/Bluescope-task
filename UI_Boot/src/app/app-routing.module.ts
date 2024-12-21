import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './Components/create/create.component';
import { ListComponent } from './Components/list/list.component';
import { DetailsComponent } from './Components/details/details.component';
import { CreateformjsonComponent } from './Components/createformjson/createformjson.component';
import { MainpageComponent } from './Components/mainpage/mainpage.component';
import { SidebarComponent } from './Components/sidebar/sidebar.component';
import { DetailformComponent } from './Components/detailform/detailform.component';
import { ListformComponent } from './Components/listform/listform.component';

const routes: Routes = [
  { path: 'create', component: CreateComponent },
  { path: 'list', component: ListComponent },
  { path: 'CreateformjsonComponent', component: CreateformjsonComponent },
  { path: 'MainpageComponent', component: MainpageComponent },
  { path: 'SidebarComponent', component: SidebarComponent },
  { path: 'listformjsonComponent', component: ListformComponent },
  { path: 'detailsformjsonComponent', component: DetailformComponent },
  { path: 'details', component: DetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
