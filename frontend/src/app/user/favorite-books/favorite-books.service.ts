import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LikedBook } from './liked-book/liked-book';

@Injectable({
  providedIn: 'root'
})
export class FavoriteBooksService {
  private backendUrl = "http://localhost:8080/favoritebook/";

  constructor(private client: HttpClient){}

  getFavoriteBooks(): Observable<LikedBook[]>{
    return this.client.get<LikedBook[]>("http://localhost:8080/favorites")
  }

  getFavoriteBookIds(): Observable<string[]>{
    return this.client.get<string[]>("http://localhost:8080/likedBooksIds")
  }

  likeBook(bookId: string): Observable<LikedBook>{
    return this.client.post<LikedBook>(this.backendUrl+"like/" + bookId , {});
  }

  unlikeBook(likedbookId: Number): Observable<void>{
    return this.client.delete<void>(this.backendUrl+"unlike/" + likedbookId);
  }

  unlikeBookByBookId(bookId: string): Observable<void>{
    return this.client.delete<void>(this.backendUrl+"delete/" + bookId);
  }

}
