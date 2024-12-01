import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import {MenubarModule} from 'primeng/menubar';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { LoginService } from './login.service';
import { PasswordModule } from 'primeng/password';
import { MessagesModule } from 'primeng/messages';
import { Message } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { RegisterComponent } from "../register/register.component";


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ToastModule, MessagesModule, ReactiveFormsModule, PasswordModule, CalendarModule, MenubarModule, CommonModule, FormsModule, InputIconModule, InputTextModule, IconFieldModule, DialogModule, RegisterComponent],
  providers: [MessageService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {
  @Input() visible: boolean = false;
  visibleRegistry: boolean = false;
  visibleLoginError: boolean = false;
  messages: Message[] = [];


  constructor(
    private loginService: LoginService = inject(LoginService),
    private messageService: MessageService
  ){}

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });


  submitLogin(){
    let token: string = "";
    this.loginService.login(this.loginForm.value.username ?? '', this.loginForm.value.password ?? '').subscribe({
      next: (response: any) => {(token = response.accessToken);
        this.loginService.addTokenToStorage(token);
        console.info(token);
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Successful login' });
        this.visible = false;
      },
      error: (error) => {this.messages = [{ severity: 'error', detail: error.error.details }];
      this.visibleLoginError = true;
    },
    });;
  }

  @Output() closeDialog = new EventEmitter<any>();
  close() {
    this.closeDialog.emit();
  }

}
