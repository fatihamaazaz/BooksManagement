import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { Book } from './book/book';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GoogleApiService {
  private apiUrl = "https://www.googleapis.com/books/v1/volumes?q=";
  private apiRetreiveBook = "https://www.googleapis.com/books/v1/volumes/";

  constructor(private client: HttpClient) { }

  public getBooks(query: String): Observable<Book[]>{
    return this.client.get<Book[]>(this.apiUrl+query+"&startIndex=0&maxResults=40").pipe(
      map((res: any) => res.items.map((item: any) => this.transformToBook(item))),
      catchError((error: any) => this.handleError(error))
  );
  }

  public getBookById(id: String): Observable<Book>{
    return this.client.get<Book>(this.apiRetreiveBook+id).pipe(map((res: any) => this.transformToBook(res)))
  }


  public transformToBook(item: any): Book{
    const volumeInfo = item.volumeInfo;
    return {
      id: item.id,
      title: volumeInfo.title ?? "",
      subtitle: volumeInfo.subtitle ?? "",
      authors: volumeInfo.authors ?? [],
      description: volumeInfo.description ?? "",
      imageLink: volumeInfo.imageLinks?.thumbnail ?? "",
      pageCount: volumeInfo.pageCount ?? 0,
      language: volumeInfo.language ?? "",
      link: volumeInfo.previewLink ?? "", 
      categories: volumeInfo.categories ?? "",
      publishedDate: volumeInfo.publishedDate ?? "",
      publisher: volumeInfo.publisher ?? ""
    }
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';

    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Server-side error: ${error.status} - ${error.message}`;
    }

    // Log the error (optional)
    console.error(errorMessage);

    // Return an observable with a user-facing error message
    return throwError(() => new Error(errorMessage));
  }

}
