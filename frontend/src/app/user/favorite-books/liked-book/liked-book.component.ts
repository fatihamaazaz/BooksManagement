import { Component, Input } from '@angular/core';
import { Book } from '../../../google-api/book/book';
import { GoogleApiService } from '../../../google-api/google-api.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-liked-book',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './liked-book.component.html',
  styleUrl: './liked-book.component.scss'
})
export class LikedBookComponent {
  @Input() bookId: string = "";
  book: Book | undefined;

  constructor(private googleService: GoogleApiService){
  }

  ngOnInit(){
    this.getBookById();
  }

  getBookById(){
    this.googleService.getBookById(this.bookId).subscribe({
      next: (resp: Book) => {(this.book = resp); 
      },
      error: (error) => {console.info(error.error);
    },})
  }

}
