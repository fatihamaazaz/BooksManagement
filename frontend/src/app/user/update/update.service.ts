import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpdateService {
  private backendUrl = "http://localhost:8080/update";

  constructor(private client: HttpClient, 
    private router: Router) { }

  update(userName: string, email: string, password: string, date: Date) : Observable<any>{
    let birthDate = formatDate(date, 'yyyy-MM-dd', 'en-US');
    return this.client.put<any>(this.backendUrl, {userName, email, password, birthDate});
  }
}
