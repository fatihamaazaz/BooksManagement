import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ScrollPanel } from 'primeng/scrollpanel';
import { loggingInterceptor } from './auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
  importProvidersFrom([ScrollPanel]), 
  importProvidersFrom([BrowserAnimationsModule]), 
  provideZoneChangeDetection({ eventCoalescing: true }), 
  provideRouter(routes), 
  provideClientHydration(), 
  provideHttpClient(withInterceptors([loggingInterceptor]))]
};
