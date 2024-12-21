import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DatajsonModule } from '../Models/datajson/datajson.module';
import { ListModule } from '../Models/list/list.module';
import { DetailModule } from '../Models/detail/detail.module';

@Injectable({
  providedIn: 'root'
})
export class BootserviceService {
  private baseUrl = 'http://localhost:8080/ftlCheck';

  constructor(private http: HttpClient) { }

  generateJsonToJsonCreate(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/Create`, jsonContent);
  }

  generateJsonToJsonList(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/List`, jsonContent);
  }

  generateJsonToJsonDetails(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/Details`, jsonContent);
  }

  generateJson(jsonContent: DatajsonModule): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/createform`, jsonContent);
  }

  generateJsonlist(jsonContent: ListModule): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/listform`, jsonContent);
  }

  generateJsondetails(jsonContent: DetailModule): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/detailsform`, jsonContent);
  }

}
