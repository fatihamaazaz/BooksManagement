import { Component, Input } from '@angular/core';
import { Book } from '../../../google-api/book/book';
import { GoogleApiService } from '../../../google-api/google-api.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-tracked-book',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './tracked-book.component.html',
  styleUrl: './tracked-book.component.scss'
})
export class TrackedBookComponent {
  book: Book | undefined;

  @Input() bookId: string = "";

  constructor(private googleService: GoogleApiService){
  }

  ngOnInit() {
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
