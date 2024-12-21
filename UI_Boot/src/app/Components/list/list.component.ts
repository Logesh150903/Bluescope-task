import { Component } from '@angular/core';
import { BootserviceService } from '../../Services/bootservice.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrl: './list.component.css'
})
export class ListComponent {
  title = 'Create';
  jsonContent: any;
  json: string | null = null;
  outputJson: string | null = null;

  constructor(private apiService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.jsonContent);
    this.json = JSON.parse(this.jsonContent);

    this.apiService.generateJsonToJsonList(this.json).subscribe({
      next: (response: any) => {
        console.log(this.outputJson);
        this.outputJson = JSON.stringify(response, null, 2);
        console.log(this.outputJson);
      },
      error: (err: any) => {
        console.error('Error occurred:', err);
        console.log(this.outputJson);
        this.outputJson = 'Error occurred while processing.';
        console.log(this.outputJson);
      }
    });
  }

}
