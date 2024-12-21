import { Component } from '@angular/core';
import { BootserviceService } from '../../Services/bootservice.service';
import { ListModule } from '../../Models/list/list.module';

@Component({
  selector: 'app-listform',
  templateUrl: './listform.component.html',
  styleUrl: './listform.component.css'
})
export class ListformComponent {
  ListModule: ListModule = {
    name: '',
    status: ''
  };
  outputJson: string | null = null;
  constructor(private customerService: BootserviceService) { }

  onSubmit(): void {
    console.log(this.ListModule);

    this.customerService.generateJsonlist(this.ListModule).subscribe({
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
