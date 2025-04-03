import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { StorageService } from '../auth/storage.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent implements OnInit{

  user: any;
  anagrafica: any;

  constructor(private authService: AuthService, private store: StorageService, ) {}
  
  ngOnInit(): void {
    this.store.isLogged();
    this.store.getUser().subscribe({
      next: resp =>{
        if(resp){
          this.user = {...resp};
          console.log("Risposta ricevuta:", resp);
        }
      },
      error: error =>{
        console.error("Errore caricamento Utente", error);
      }
    });
  }

  onSubmit(): void {
    var username = this.store.getUsername();
    var token = this.store.getAuth();
    this.authService.getUser(username, token).subscribe({
      next: data => {
        this.user = data;
      },
      error: error => {
        console.error('Errore recupero utente', error);
      }
    });
  }

  clearUserHome () {
    this.user = null;
  }
}
