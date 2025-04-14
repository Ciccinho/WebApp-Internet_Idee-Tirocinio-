import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { AnagraficaComponent } from '../entity/anagrafica/anagrafica.component';

const USER_KEY = 'username-user';
const PASS_KEY = 'password-user';
const TOKEN_KEY = 'auth-user';
const COD_FISC = 'cf';
const TIPO_SOGG = 'tipo';

@Injectable({
  providedIn: 'root'
})

export class StorageService {

  anagrafica: AnagraficaComponent = new AnagraficaComponent;

  constructor(private auth: AuthService, private router: Router) { }

  
  public saveUser(username: string, password: string, token: string, cf: string, tipo: any): void {
    localStorage.setItem(USER_KEY, username);
    localStorage.setItem(PASS_KEY, password);
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(COD_FISC, cf);
    localStorage.setItem(TIPO_SOGG, tipo);
  }

  public getUsername(): string {
    var username =  localStorage.getItem(USER_KEY);
    return username!;
  }

  public getAuth(): string {
    var auth = localStorage.getItem(TOKEN_KEY);
    return auth!;
  }

  public isLogged( ): boolean {
    const token = localStorage.getItem(TOKEN_KEY);
    return !!token;
  }

  public getUser(): Observable<any> {
    var username = this.getUsername();
    var token = this.getAuth();
    if(!username || !token){
      console.error("Username o token non trovato");
      return new Observable();
    }
    return this.auth.getUser(username, token!);
  }

  public getTipo(): any {
    var tipo = localStorage.getItem(TIPO_SOGG);
    return tipo;
  }

  public getCf(): any {
    var cf = localStorage.getItem(COD_FISC);
    return cf;
  }
 
  public redirectNewRouter(): void {
    var isLogIn: boolean = this.isLogged();
    if(!isLogIn)
      this.router.navigate(['/login']);
  }

  public clean (): void {
    console.log("Richiesta logout: cancellazione user dal SesssionStorage");
    localStorage.clear();
  }

}
