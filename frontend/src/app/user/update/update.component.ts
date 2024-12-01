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
import { MessagesModule } from 'primeng/messages';
import { Message } from 'primeng/api';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';
import { UpdateService } from './update.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { LoginService } from '../../login/login.service';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [ToastModule, MessagesModule, ReactiveFormsModule, PasswordModule, CalendarModule, MenubarModule, CommonModule, FormsModule, InputIconModule, InputTextModule, IconFieldModule, DialogModule],
  providers: [MessageService],
  templateUrl: './update.component.html',
  styleUrl: './update.component.scss'
})
export class UpdateComponent {

  @Input() visible: boolean = false;
  visibleError: boolean = false;
  @Input() messages: Message[] = [];
  @Input() user : User | undefined;

  constructor(private messageService: MessageService,
              private updateService: UpdateService = inject(UpdateService),
              private loginService: LoginService = inject(LoginService)){}

  updateForm = new FormGroup({
    username: new FormControl(),
    email: new FormControl(),
    password: new FormControl(),
    birthDate: new FormControl()
  });

  submitUpdate(){
    this.updateService.update(this.updateForm.value.username || this.user?.userName, this.updateForm.value.email || this.user?.email, 
                                  this.updateForm.value.password || '',this.updateForm.value.birthDate || this.user?.birthDate)
                                  .subscribe({
                                    next: (response: any) => {
                                      this.visible = false;
                                      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Account has been created successfuly' });
                                      if(this.updateForm.value.username != this.user?.userName && this.updateForm.value.username != "" && this.updateForm.value.username != null){
                                        console.info(this.updateForm.value.username)
                                        console.info(this.user?.userName)
                                        this.loginService.logout();
                                      }
                                    },
                                    error: (error) => {this.messages = [{ severity: 'error', detail: error.error.details }];
                                      this.visibleError = true;
                                    },
                                  });
  }

  @Output() closeDialog = new EventEmitter<any>();
  close() {
    this.closeDialog.emit();
  }


}
