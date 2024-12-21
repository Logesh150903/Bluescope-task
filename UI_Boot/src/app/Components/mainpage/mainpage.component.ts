import { Component } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrl: './mainpage.component.css'
})
export class MainpageComponent {
  url: string = '';
  dropdown: string | null = null;

  toggleDropdown(type: string) {
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
