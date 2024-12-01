import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Book } from './book';
import { GoogleApiService } from '../google-api.service';
import { DividerModule } from 'primeng/divider';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { FieldsetModule } from 'primeng/fieldset';
import { CommonModule } from '@angular/common';
import { ScrollPanelModule } from 'primeng/scrollpanel'; 
import { LoginService } from '../../login/login.service';


@Component({
  selector: 'app-book',
  standalone: true,
  imports: [DividerModule, ButtonModule, CardModule, FieldsetModule, CommonModule, ScrollPanelModule],
  templateUrl: './book.component.html',
  styleUrl: './book.component.scss'
})
export class BookComponent {

  book: Book | undefined;
  booksService: GoogleApiService = inject(GoogleApiService);

  constructor(private route: ActivatedRoute,
              public loginService: LoginService
  ) {}

  ngOnInit(): void {
    this.getBookFromPath();
  }

  

  getBookFromPath(){
    this.route.params.subscribe(params => {
      const id = params['id'] || null;
      if (id) {
        this.booksService.getBookById(id).subscribe((res : Book) => (this.book = res));
      } else {
        console.info("Query parameter is empty.");
      }
    });
  }

}
