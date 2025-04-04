import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const urlAuth = 'http://localhost:8080/api/auth/';   //indirizzo del server
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

  getAnagraficaUser(username: string, token: string): Observable<any>{
    const header = new HttpHeaders({'Content-Type':'application/json', 'Authorization':`Bearer ${token}`});
    return this.http.get(urlAuth+'getAnagrafica/', { headers: header, params: {username}});
  }
}