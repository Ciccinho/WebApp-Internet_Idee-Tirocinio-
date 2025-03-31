import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent implements OnInit{

  user: any;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getUser().subscribe({
      next: response => {
        if(response)
          this.user = {...response};
        else 
          this.router.navigate(['/login']);
      },
      error: error => {
        console.error('Errore nel recupero utente', error);
        this.router.navigate(['/login']);
      }
    });
  }

  clearUserHome () {
    this.user = null;
  }
}
