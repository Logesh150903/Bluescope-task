import { Component } from '@angular/core';
import { BootserviceService } from '../../Services/bootservice.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrl: './details.component.css'
})
export class DetailsComponent {
  title = 'Create';
  jsonContent: any;
  json: string | null = null;
  outputJson: string | null = null;

  constructor(private apiService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.jsonContent);
    this.json = JSON.parse(this.jsonContent);

    this.apiService.generateJsonToJsonDetails(this.json).subscribe({
      next: (response: any) => {
        this.outputJson = JSON.stringify(response, null, 2);
      },
      error: (err: any) => {
        console.error('Error occurred:', err);
        this.outputJson = 'Error occurred while processing.';
      }
    });
  }
}
