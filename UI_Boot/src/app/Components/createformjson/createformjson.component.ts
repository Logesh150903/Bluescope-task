import { Component } from '@angular/core';
import { DatajsonModule } from '../../Models/datajson/datajson.module';
import { BootserviceService } from '../../Services/bootservice.service';

@Component({
  selector: 'app-createformjson',
  templateUrl: './createformjson.component.html',
  styleUrl: './createformjson.component.css'
})
export class CreateformjsonComponent {
  DatajsonModule: DatajsonModule = {
    theirReferenceId: '',
    city: '',
    customerName: '',
    amount: 0,
    currency: '',
  };
  outputJson: string | null = null;
  constructor(private customerService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.DatajsonModule);

    this.customerService.generateJson(this.DatajsonModule).subscribe({
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
