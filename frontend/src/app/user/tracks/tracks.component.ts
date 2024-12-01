import { Component, Input } from '@angular/core';
import { DataViewModule } from 'primeng/dataview';
import { TrackedBook } from './tracked-book/tracked-book';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { TrackedBooksService } from './tracks.service';
import { TrackedBookComponent } from "./tracked-book/tracked-book.component";
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { StepsModule } from 'primeng/steps';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';


@Component({
  selector: 'app-book-trucks',
  standalone: true,
  imports: [DataViewModule, CommonModule, ButtonModule, TrackedBookComponent, StepsModule, ToastModule, ConfirmDialogModule],
  providers: [MessageService, ConfirmationService],
  templateUrl: './tracks.component.html',
  styleUrl: './tracks.component.scss'
})
export class BookTrucksComponent {
  books: TrackedBook[] = []
  items: MenuItem[] | undefined;
  
  constructor(private trackedBooksService:TrackedBooksService, 
              public messageService: MessageService,
              private confirmationService: ConfirmationService){}


  ngOnInit() {
    this.getTrackedBooks();
    this.items = [
        {
            label: "Beginning",
            command: (event: any) => this.messageService.add({severity:'success', summary:'Status changed successfuly to ', detail: event.item.label})
        },
        {
            label: "Developing",
            command: (event: any) => this.messageService.add({severity:'success', summary:'Status changed successfuly to ', detail: event.item.label})
        },
        {
            label: "Middle",
            command: (event: any) => this.messageService.add({severity:'success', summary:'Status changed successfuly to ', detail: event.item.label})
        },
        {
            label: "End",
            command: (event: any) => this.messageService.add({severity:'success', summary:'Status changed successfuly to ', detail: event.item.label})
        }
    ];
  }

  onActiveIndexChange(event: number, trackedBook: TrackedBook) {
    let book: TrackedBook;
    console.info("we are in update")
    this.trackedBooksService.updateTrackedBookStatus(trackedBook.id, trackedBook.bookId, this.getStatus(event)).subscribe({
      next: (resp: TrackedBook) => {(book = resp);
        this.getTrackedBooks();
      },
      error: (error) => {console.info(error.error)}
    })
  }
 
  getStatusNumber(status: string): Number{
    switch (status) {
      case "BEGINNING":
          return 0
          break;
      case "DEVELOPING":
          return 1
          break;
      case "MIDDLE":
          return 2
          break;
      case "END":
          return 3
          break;
      default:
          return 404
    }
  }

  getStatus(event: Number): string{
    switch (event) {
      case 0:
          return "BEGINNING"
          break;
      case 1:
          return "DEVELOPING"
          break;
      case 2:
          return "MIDDLE"
          break;
      case 3:
          return "END"
          break;
      default:
          return ""
    }
  }

  getTrackedBooks(){
    this.trackedBooksService.getTrackedBooks().subscribe({
      next: (resp: TrackedBook[]) => {(this.books = resp);
      },
      error: (error) => {console.info(error.error)}
    })
  }

  deleteTrackedBook(id: Number){
    this.trackedBooksService.untrackBook(id).subscribe({
      next: () => { 
        this.getTrackedBooks();
      },
      error: (err) => {
        this.messageService.add({severity:'error', summary:'Could not untrack the book because of the error', detail: err.error})
      },
    });
  }

  confirm1(event: Event, id: Number) {
    this.confirmationService.confirm({
        target: event.target as EventTarget,
        message: 'Are you sure you want to untrack that book?',
        header: 'Confirmation',
        icon: 'pi pi-exclamation-triangle',
        acceptIcon:"none",
        rejectIcon:"none",
        rejectButtonStyleClass:"p-button-text",
        accept: () => {
            this.deleteTrackedBook(id);
            this.messageService.add({ severity: 'success', summary: 'Confirmed', detail: 'Book has been untracked successfuly' });
        },
        reject: () => {
            this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'Could not untrack the book' });
        }
    });
  }


}
