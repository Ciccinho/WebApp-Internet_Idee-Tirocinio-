import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { CommonModule } from '@angular/common';
import { StorageService } from '../auth/storage.service';
import { AnagraficaComponent } from '../entity/anagrafica/anagrafica.component';
import { saveAs } from 'file-saver';
import * as XLSX from 'xlsx';


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
  tipo: boolean =  false;
  tipoSogg: string = '';
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
        this.tipo = data.personaFisica;
        this.cf = data.codiceFiscale;
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
    this.authService.getReport(this.token).subscribe({  
      next: (response: Blob)=>{
        response.arrayBuffer().then( buffer =>{
          const workbook = XLSX.read( buffer, {type: 'array'});
          const workBlob = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
          const blob = new Blob ([workBlob],{ type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          saveAs(blob,'repo_catasto.xlsx');
        });
      },
      error: (error)=>{
        console.error('Errore nel download del file', error);
        this.errorMsg = `Errore${error.status}: ${error.message}`;
      }
    });
  }

  logout(): void {
    this.store.clean();
    this.showAnagrafica = false;
    window.location.href = '/login';
  }

}