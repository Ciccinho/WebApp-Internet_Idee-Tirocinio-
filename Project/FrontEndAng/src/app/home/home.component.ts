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
  showAnagrafica: boolean = false;
  tipo: string = '';
  cf: string = ''
 
  constructor(private authService: AuthService, private store: StorageService, ) {}
  

  ngOnInit(): void {
    if(this.store.isLogged()){
      this.token = this.store.getAuth();
      this.user = this.store.getUsername();
    } else {
      this.errorMsg = 'Utente non autenticato. Esegui login.';
    }
  }

  visualizzaAnagrafica(): void {
    this.authService.getAnagraficaUser(this.token).subscribe({
      next: (data)=>{ 
        this.anagrafica = data;
        console.log('data caricata in anagrafica: ');
        this.showAnagrafica= true;
        this.errorMsg ='';
      },
      error: (error)=>{ 
        console.error("Errore caricamento Utente", error);
        this.errorMsg = `Errore ${error.status}: ${error.name}`;
        this.showAnagrafica = false;
      }
    });
  } 

  chiudiAnagrafica(): void {
    this.showAnagrafica = false;
  }  

  scaricaInfo():void {
    this.cf = this.anagrafica.codiceFiscale;
    if(this.anagrafica.personaFisica)
      this.tipo = 'F';
    else
      this.tipo = 'G';
    this.authService.getCatastoReport(this.tipo, this.cf).subscribe({
      next: (blob: any)=>{
        const url = window.URL.createObjectURL(blob);
        const repo = document.createElement('repo') as HTMLAnchorElement;
        repo.href = url;
        repo.download = 'catasto_report.xlsx';
        document.body.appendChild(repo);
        repo.click();
        document.body.removeChild(repo);
        window.URL.revokeObjectURL(url);
      },
      error: (error)=>{
        console.error('Errore nel download del file', error);
        this.errorMsg = `Errore${error.status}: ${error.name}`;
      }
    });
  }

  logout(): void {
    this.store.clean();
    this.showAnagrafica = false;
    window.location.href = '/login';
  }

}