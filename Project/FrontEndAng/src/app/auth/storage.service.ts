import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

const USER_KEY = 'username-user';
const PASS_KEY = 'password-user';
const TOKEN_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})

export class StorageService {

  constructor(private auth: AuthService) { }

  public saveUser(username: string, password: string, token: string): Observable<boolean> {
    window.sessionStorage.setItem(USER_KEY, username);
    window.sessionStorage.setItem(PASS_KEY, password);
    window.sessionStorage.setItem(TOKEN_KEY, token);
    return new Observable( value =>{
      value.next(true);
    });
  }

  public isLogged( ): boolean {
    const token = window.sessionStorage.getItem(TOKEN_KEY);
    return !!token;
  }

  public clean (): void {
    console.log("Richiesta logout: cancellazine user dal SesssionStorage");
    window.sessionStorage.clear();
  }

}
