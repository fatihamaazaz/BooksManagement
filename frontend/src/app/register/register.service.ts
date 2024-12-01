import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {
  private backendUrl = "http://localhost:8080/register";

  constructor(private client: HttpClient){}
  
  register(userName: string, email: string, password: string, birth: Date) : Observable<any>{
    let token: string = "";
    let birthDate = formatDate(birth, 'yyyy-MM-dd', 'en-US');
    console.info(birthDate);
    return this.client.post<any>(this.backendUrl, {userName, email, password, birthDate})
  }
}
