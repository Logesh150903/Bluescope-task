import { Component } from '@angular/core';
import { BootserviceService } from '../../Services/bootservice.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent {
  jsonContent: any;
  json: string | null = null;
  outputJson: string | null = null;

  constructor(private apiService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.jsonContent);
    this.json = JSON.parse(this.jsonContent);
    // this.jsonContent =JSON.stringify(this.jsonContent);

    this.apiService.generateJsonToJsonCreate(this.json).subscribe({
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
