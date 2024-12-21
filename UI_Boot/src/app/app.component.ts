import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'UI_Boot';
  url: string = '/';

  constructor(
    private route: Router
  ) { }

  gotourl(url: string): void {
    this.route.navigate(["/" + url]);
  }
}