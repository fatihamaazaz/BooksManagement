import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private backendUrl = "http://localhost:8080/login";
  private loggedIn: boolean = false;
  static platformId: Object;

  constructor(private client: HttpClient, 
    private router: Router,
    @Inject(PLATFORM_ID) platformId: Object) { LoginService.platformId = platformId }

  login(username: string, password: string) : Observable<any>{
    return this.client.post<any>(this.backendUrl, {username, password});
  }

  addTokenToStorage(token: string){
    if (isPlatformBrowser(LoginService.platformId)) {
      localStorage.setItem("authToken", token);
    }
  }

  logout(): void {
    if (isPlatformBrowser(LoginService.platformId)) {
      localStorage.removeItem('authToken');
      this.loggedIn = false;
    }
  }

  isLoggedIn(): boolean {
    if (isPlatformBrowser(LoginService.platformId)) {
      let token: string = (localStorage.getItem('authToken') || "");
      this.loggedIn = !this.isTokenExpired(token);
    }
    return this.loggedIn;
  }

  isTokenExpired(token: string) {
    if (!token) return true;

    const payloadBase64 = token.split('.')[1];
    const payloadDecoded = JSON.parse(atob(payloadBase64));
  
    const now = Math.floor(Date.now() / 1000); // en secondes
    return payloadDecoded.exp < now;
  }

  static getToken(): string{
    let token: string = "";
    if (isPlatformBrowser(LoginService.platformId)) {
      token = localStorage.getItem('authToken') || '';
    }
    console.info(token)
    return token;
  }

}
