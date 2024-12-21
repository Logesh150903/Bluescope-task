import { Component } from '@angular/core';
import { BootserviceService } from '../../Services/bootservice.service';
import { DetailModule } from '../../Models/detail/detail.module';

@Component({
  selector: 'app-detailform',
  templateUrl: './detailform.component.html',
  styleUrl: './detailform.component.css'
})
export class DetailformComponent {
  DetailModule: DetailModule = {
    actioncode: ''
  };
  outputJson: string | null = null;
  constructor(private customerService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.DetailModule);

    this.customerService.generateJsondetails(this.DetailModule).subscribe({
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
