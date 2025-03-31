import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { StorageService } from '../auth/storage.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {

  form: any = {username: null, password: null}
  username: string ='';
  password: string = '';
  isLogged = false;
  isFailledLog = false;
  errorMess: string = '';
  

  constructor(private authService: AuthService, private storage: StorageService) {}

  onSubmit(): void {
    const {username, password}=this.form;
    this.username = username;
    this.password = password;
    this.authService.login(username, password).subscribe({
      next: data => {
        this.storage.saveUser(data.token, this.username, this.password).subscribe(
          response => {
            if(response){
              console.log('data', data);
              this.isFailledLog = false;
              this.isLogged = true;
            }
          });
      },
      error: err => {
        this.errorMess = err.message;
        this.isFailledLog = false;
      }
    });
  }

}