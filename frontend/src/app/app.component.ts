import { Component, inject } from '@angular/core';
import {MenubarModule} from 'primeng/menubar';
import {MenuItem, Message} from 'primeng/api';
import { CommonModule } from '@angular/common';
import { Book } from './google-api/book/book';
import { FormsModule } from '@angular/forms';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { Router, RouterLink } from '@angular/router';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { ReactiveFormsModule} from '@angular/forms';
import { LoginService } from './login/login.service';
import { LoginComponent } from "./login/login.component";
import { MenuModule } from 'primeng/menu';
import { UpdateComponent } from "./user/update/update.component";
import { UserService } from './user/user.service';
import { User } from './user/user';
import { TrackedBooksService } from './user/tracks/tracks.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ReactiveFormsModule, MenuModule, CalendarModule, MenubarModule, CommonModule, FormsModule, InputIconModule, InputTextModule, IconFieldModule, DialogModule, ButtonModule, LoginComponent, UpdateComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent {
  items: MenuItem[] = [];
  visibleLogin: boolean = false;
  visibleRegistry: boolean = false;
  visibleUpdate: boolean = false;
  messages: Message[] = [];
  user : User | undefined;


  constructor(private router: Router,
              private userService:UserService,
              public loginService: LoginService,
              private trackedBooksService: TrackedBooksService
  ) {}

  ngOnInit() {
      this.items = [{
        label:'',
        items:[
            {
                label:'Favorite books',
                icon:'pi pi-fw pi-star',
                route: '/favoritebooks'
            },
            {
                label:'Tracked books',
                icon:'pi pi-fw pi-thumbtack',
                route: '/trackedbooks'
            },
            {
                label:'Update profil',
                icon:'pi pi-fw pi-pencil',
                command: () => {this.getUserData(); this.visibleUpdate = true;}
            },
            {
              separator:true
            },
            {
                label:'Logout',
                icon:'pi pi-fw pi-sign-out',
                command: () => {this.loginService.logout()}
            }
        ]
      }];
  }

  booksList: Book[] = [];
  query: String = "";

  onEnter(): void {
    if (this.query) {
      this.router.navigate(['/books'], { queryParams: { query: this.query } });
    } else {
        console.warn("Query is empty. Please enter a search term.");
    }
  } 

  showLoginDialog() {
    console.log('Boîte de dialogue affichée');
    this.visibleLogin = true;
  }

  getUserData(){
    this.userService.getUserByToken().subscribe({
      next: (resp: User) => {(this.user = resp);
      },
      error: (error) => {this.messages = [{ severity: 'error', detail: error.error.details }];
      
    },})
  }

}
