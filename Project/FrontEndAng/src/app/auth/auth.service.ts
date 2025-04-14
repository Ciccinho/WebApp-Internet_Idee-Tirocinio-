import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const urlAuth = 'http://localhost:8080/api/auth/';   //indirizzo backend
const urlRepo = 'http://localhost:8080/api/'         //indirizzo chiamata server esterno
const httpContent = {headers: new HttpHeaders ({'Content-Type': 'application/json'})} //generalizzazione per includere i file json nella trasmissione http



@Injectable({
  providedIn: 'root'
})

export class AuthService {
  

  constructor (private  http: HttpClient) { }

  login (username: string, password: string): Observable <any>{
    return this.http.post(urlAuth +'login', {username, password}, httpContent);
  }

  getUser (username: string, token: string): Observable <any>{
    const header = new HttpHeaders ({'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`});
    return this.http.get(urlAuth + 'getUsername/'+`${username}`, { headers: header });
  }

  getAnagraficaUser(token: string): Observable<any>{
    const header = new HttpHeaders({'Content-Type':'application/json', 'Authorization':`Bearer ${token}`});
    return this.http.get(urlAuth+'getAnagrafica/', { headers: header });
  }

  

  getCatastoReport (tipo: String, cf: String): Observable<any>{
    const body = {tipo: tipo, cf: cf};
    return this.http.put(urlRepo+'catastoSintetico', body, {headers: new HttpHeaders({ 'Content-Type': 'application/json'}), responseType: 'blob'});
  }
}