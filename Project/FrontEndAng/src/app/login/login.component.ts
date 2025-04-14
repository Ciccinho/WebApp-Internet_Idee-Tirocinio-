import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { StorageService } from '../auth/storage.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent implements OnInit {


  form: any = {username: '', password: ''}
  username: any;
  password: any;
  isLog = false;
  isFailledLog = false;
  errorMess: string = '';
  anagrafica: any;
  showPopup = false;
  

  constructor(private authService: AuthService, private storage: StorageService, private route: Router) {}

  ngOnInit(): void {
    if(this.storage.isLogged()){
      this.isLog = true;
      this.username = this.storage.getUsername();
    }
  }

  login(): void {
    const {username, password} = this.form;
    this.username = username;
    this.password = password;
    this.authService.login(this.username, this.password).subscribe({
      next: data => {
        this.storage.saveUser(this.username, this.password, data.token);
        this.isFailledLog = false;
        this.isLog = true;
        this.showPopup = true;
        if(this.storage.isLogged()){
          setTimeout(()=> {
            this.showPopup = false;
            this.goToAnagrafica();
          }, 2000);         
        }
      },
      error: err => {
        this.errorMess = `Errore ${err.status}: ${err.name}`;
        this.isFailledLog = true;
      }
    });
    
  }

  goToAnagrafica (): void {
    this.route.navigate(['/home']);
  }

  logout():void {
    this.storage.clean();
    this.isLog = false;
    this.route.navigate(['/login']);
  }
}