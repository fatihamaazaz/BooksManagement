import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';
import { TrackedBook } from './tracked-book/tracked-book';
import { GoogleApiComponent } from '../../google-api/google-api.component';
import { GoogleApiService } from '../../google-api/google-api.service';

@Injectable({
  providedIn: 'root'
})
export class TrackedBooksService {
  private backendUrl = "http://localhost:8080/booktrack/";

  constructor(private client: HttpClient,
              private googleService: GoogleApiService
  ){}

  getTrackedBooks(): Observable<TrackedBook[]>{
    return this.client.get<TrackedBook[]>("http://localhost:8080/tracks");
  }

  getTrackedBooksIds(): Observable<string[]>{
    return this.client.get<string[]>("http://localhost:8080/trackedBooksIds");
  }

  deleteTrackByBookId(bookId: string): Observable<void>{
    return this.client.delete<void>(this.backendUrl+bookId);
  }

  trackBook(bookId: string): Observable<TrackedBook>{
    return this.client.post<TrackedBook>(this.backendUrl + "add/" + bookId, {})
  }

  untrackBook(trackedBookId: Number): Observable<any>{
    return this.client.delete(this.backendUrl+"delete/"+trackedBookId);
  }

  updateTrackedBookStatus(id: Number, bookId: string, status: string) : Observable<TrackedBook>{
    return this.client.put<TrackedBook>(this.backendUrl+"update",{id, bookId, status});
  }

}
