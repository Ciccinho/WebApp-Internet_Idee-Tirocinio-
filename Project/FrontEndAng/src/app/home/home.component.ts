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
  errorMsg: String='';
 
  constructor(private authService: AuthService, private store: StorageService, ) {}
  
  ngOnInit(): void {
    if(this.store.isLogged()){
      this.token = this.store.getAuth();
      this.authService.getAnagraficaUser(this.token).subscribe({
        next: (data)=>{ 
          this.anagrafica = data;
          this.errorMsg = '';
          console.log('data caricata in anagrafica: ', data);
        },
        error: (error)=>{ 
          console.error("Errore caricamento Utente", error);
          if (error.status === 0) {
            this.errorMsg = 'Server non raggiungibile. Verifica la connessione.';
          } else if (error.status === 401) {
            this.errorMsg = 'Sessione scaduta. Effettua di nuovo il login.';
            // Puoi anche fare redirect automatico al login se vuoi
          } else if (error.status === 404) {
            this.errorMsg = 'Dati utente non trovati.';
          } else {
            this.errorMsg = `Errore imprevisto (${error.status}): ${error.statusText}`;
          }
        }
      });
    } else {
      this.errorMsg = 'Utente non autenticato. Effettua il login.';
    }
  }



  clearUserHome(): void {
    this.store.clean();
  }
}
