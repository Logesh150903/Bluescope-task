import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  url: string = '/';
  dropdown: string | null = null;

  Dropdown(type: string) {
    if (this.dropdown === type) {
      this.dropdown = null;
    } else {
      this.dropdown = type;
    }
  }
  constructor(
    private route: Router
  ) { }

  gotourl(url: string): void {
    this.route.navigate(["/" + url]);
  }
}
