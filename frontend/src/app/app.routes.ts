import { Routes } from '@angular/router';
import { GoogleApiComponent } from './google-api/google-api.component';
import { BookComponent } from './google-api/book/book.component';
import { LoginComponent } from './login/login.component';
import { BookTrucksComponent } from './user/tracks/tracks.component';
import { FavoriteBooksComponent } from './user/favorite-books/favorite-books.component';

export const routes: Routes = [
    {  path: 'book/:id',    component: BookComponent },  
    {  path: 'books', component: GoogleApiComponent},
    {  path: 'login', component: LoginComponent},
    {  path: 'trackedbooks', component: BookTrucksComponent},
    {  path: 'favoritebooks', component: FavoriteBooksComponent}
];
