import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { StorageService } from '../auth/storage.service';
import { AnagraficaComponent } from '../entity/anagrafica/anagrafica.component';

@Component({
  selector: 'app-home',
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent implements OnInit{

  user: string = '';
  token: string ='';
  anagrafica: AnagraficaComponent = new AnagraficaComponent;

  constructor(private authService: AuthService, private store: StorageService, ) {}
  
  ngOnInit(): void {
    if(this.store.isLogged()){
      this.token = this.store.getAuth();
      this.authService.getAnagraficaUser(this.token).subscribe({
        next: (data)=>{ this.anagrafica = data;
          console.log('data caricata in anagrafica: ', data);
        },
        error: (error)=>{ console.error("Errore caricamento Utente", error);
        }
      });
    }
  }

  clearUserHome(): void {
    this.store.clean();
  }
}
