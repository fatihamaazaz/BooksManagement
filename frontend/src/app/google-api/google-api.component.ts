import { Component, inject, Input} from '@angular/core';
import { Book } from './book/book';
import { CommonModule } from '@angular/common';
import {FormsModule} from '@angular/forms';
import { DataViewModule } from 'primeng/dataview';
import { ButtonModule } from 'primeng/button';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { GoogleApiService } from './google-api.service';
import { LoginService } from '../login/login.service';
import { TrackedBooksService } from '../user/tracks/tracks.service';
import { FavoriteBooksService } from '../user/favorite-books/favorite-books.service';

@Component({
  selector: 'app-google-api',
  standalone: true,
  imports: [CommonModule, FormsModule, DataViewModule, ButtonModule, RouterLink, RouterOutlet],
  templateUrl: './google-api.component.html',
  styleUrl: './google-api.component.scss'
})

export class GoogleApiComponent {
  books: Book[] = [];
  booksService: GoogleApiService = inject(GoogleApiService);
  trackedBooksIds: string[] = [];
  likedBooksIds: string[] = [];
  
  constructor(private route: ActivatedRoute,
              public loginService: LoginService,
              private trackedBooksService: TrackedBooksService,
              private likedBooksService: FavoriteBooksService
  ) {}

  ngOnInit(): void {
    this.getBooksFromPath();
    this.getTrackedBooksIds();
    this.getLikedBooksIds();
  }

  getTrackedBooksIds(){
    this.trackedBooksService.getTrackedBooksIds().subscribe({
      next: (res: string[]) => { this.trackedBooksIds = res;
      },
      error: (err) => {
        console.info("Could not get tracked books ids " + err.error);
      },
    });
  }

  getLikedBooksIds(){
    this.likedBooksService.getFavoriteBookIds().subscribe({
      next: (res: string[]) => { this.likedBooksIds = res;
      },
      error: (err) => {
        console.info("Could not get liked books ids " + err.error);
      },
    });
  }

  isTrackedBook(bookId: string): boolean{
    return !this.trackedBooksIds.includes(bookId);
  }

  isLikedBook(bookId: string): boolean{
    return !this.likedBooksIds.includes(bookId);
  }

  clickTrackingButton(bookId: string){
    if(!this.isTrackedBook(bookId)){
      this.deleteTrackedBook(bookId);
    }
    else{
      this.trackBook(bookId);
    }
  }

  clickLikeButton(bookId: string){
    if(!this.isLikedBook(bookId)){
      this.deleteLikedBook(bookId);
    }
    else{
      this.likeBook(bookId);
    }
  }

  deleteTrackedBook(bookId: string){
    this.trackedBooksService.deleteTrackByBookId(bookId).subscribe({
      next: () => {
        this.getTrackedBooksIds();
      },
      error: (err) => {
      },
    });
  }

  deleteLikedBook(bookId: string){
    this.likedBooksService.unlikeBookByBookId(bookId).subscribe({
      next: () => {
        this.getLikedBooksIds();
      },
      error: (err) => {
      },
    });
  }

  trackBook(bookId: string){
    this.trackedBooksService.trackBook(bookId).subscribe({
      next: () => {
        this.getTrackedBooksIds();
      },
      error: (err) => {
      },
    });
  }

  likeBook(bookId: string){
    this.likedBooksService.likeBook(bookId).subscribe({
      next: () => {
        this.getLikedBooksIds();
      },
      error: (err) => {
      },
    });
  }

  getBooksFromPath(){
    this.route.queryParams.subscribe(params => {
      const query = params['query'] || null;
      if (query) {
        console.info("Fetching books for query: " + query);
        this.booksService.getBooks(query).subscribe((response: Book[]) => {
          this.books = response;
        });
      } else {
        console.info("Query parameter is empty.");
      }
    });
  }

}
