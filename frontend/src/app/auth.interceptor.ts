import { inject, Injectable } from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpHandlerFn} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login/login.service';

  

export function loggingInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const token = LoginService.getToken(); // Récupérer le token stocké
  console.info(token);
  if (token && req.url.includes("http://localhost:8080/") && !req.url.includes("http://localhost:8080/register")) {
    const clonedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });
    return next(clonedReq);
  }
  return next(req);
}


