import { HttpClient, HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';
import { LoginService } from '../login/login.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private backendUrl = "http://localhost:8080/";

  constructor( private client: HttpClient,
                private loginService: LoginService
  ) { }

  getUserByToken(): Observable<User>{
    return this.client.get<User>(this.backendUrl+"client");
  }

}
