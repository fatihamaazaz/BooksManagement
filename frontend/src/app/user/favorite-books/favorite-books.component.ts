import { Component, Input } from '@angular/core';
import { DataViewModule } from 'primeng/dataview';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { StepsModule } from 'primeng/steps';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { LikedBook } from './liked-book/liked-book';
import { LikedBookComponent } from "./liked-book/liked-book.component";
import { FavoriteBooksService } from './favorite-books.service';

@Component({
  selector: 'app-favorite-books',
  standalone: true,
  imports: [DataViewModule, CommonModule, ButtonModule, StepsModule, ToastModule, ConfirmDialogModule, LikedBookComponent],
  providers: [MessageService, ConfirmationService],
  templateUrl: './favorite-books.component.html',
  styleUrl: './favorite-books.component.scss'
})
export class FavoriteBooksComponent {
  books: LikedBook[] = [];

  constructor(public messageService: MessageService,
    private confirmationService: ConfirmationService,
    private favoriteBookService: FavoriteBooksService){}

  ngOnInit() {
    this.getLikedBooks();
  }

  getLikedBooks(){
    this.favoriteBookService.getFavoriteBooks().subscribe({
      next: (resp: LikedBook[]) => {(this.books = resp);
      },
      error: (error) => {console.info(error.error)}
    })
  }

  deleteLikedBook(id: Number){
    this.favoriteBookService.unlikeBook(id).subscribe({
      next: () => { 
        this.getLikedBooks();
      },
      error: (err) => {
        this.messageService.add({severity:'error', summary:'Could not unlike the book because of the error', detail: err.error})
      },
    });
  }

  confirm1(event: Event, id: Number) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Are you sure that you want to unlike that book?',
        header: 'Confirmation',
        icon: 'pi pi-exclamation-triangle',
        acceptIcon:"none",
        rejectIcon:"none",
        rejectButtonStyleClass:"p-button-text",
        accept: () => {
            this.deleteLikedBook(id);
            this.messageService.add({ severity: 'success', summary: 'Confirmed', detail: 'Book has been unliked successfuly' });
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'Could not unlike the book' });
        }
    });
  }

}
