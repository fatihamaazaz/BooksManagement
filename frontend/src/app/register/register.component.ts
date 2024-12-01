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
import { PasswordModule } from 'primeng/password';
import { RegisterService } from './register.service';
import { MessagesModule } from 'primeng/messages';
import { Message } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ToastModule, MessagesModule, ReactiveFormsModule, PasswordModule, CalendarModule, MenubarModule, CommonModule, FormsModule, InputIconModule, InputTextModule, IconFieldModule, DialogModule],
  providers: [MessageService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  @Input() visible: boolean = false;
  visibleRegisterError: boolean = false;
  messages: Message[] = [];

  constructor(private messageService: MessageService,
              private registerService: RegisterService = inject(RegisterService)){}

  registerForm = new FormGroup({
    username: new FormControl(),
    email: new FormControl(),
    password: new FormControl(),
    birthDate: new FormControl()
  });

  submitRegister(){
    this.registerService.register(this.registerForm.value.username, this.registerForm.value.email, 
                                  this.registerForm.value.password,this.registerForm.value.birthDate)
                                  .subscribe({
                                    next: (response: any) => {
                                      console.log("User registered successfully:", response);
                                      this.visible = false;
                                      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Account has been created successfuly' });
                                    },
                                    error: (error) => {this.messages = [{ severity: 'error', detail: error.error.details }];
                                      this.visibleRegisterError = true;
                                    },
                                  });
  }

  @Output() closeDialog = new EventEmitter<any>();
  close() {
    this.closeDialog.emit();
  }
  
}
