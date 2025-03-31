import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const urlAuth = '';   //indirizzo del server
const httpContent = {headers: new HttpHeaders ({'Content-Type': 'application/json'})} //generalizzazione per includere i file json nella trasmissione http

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  

  constructor( private  http: HttpClient) { }

  login (username: string, password: string): Observable <any>{
    return this.http.post(urlAuth +'', {username, password}, httpContent);
  }

  getUser (): Observable <any>{
    return this.http.get<any>(urlAuth);
  }

}