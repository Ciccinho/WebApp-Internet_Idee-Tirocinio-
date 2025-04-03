import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../auth/storage.service';
import { catchError, Observable, throwError } from 'rxjs';

const TOKEN_HEADER = 'Authorization'

@Injectable()

export class AuthInterceptor implements HttpInterceptor {

  constructor(private storage: StorageService ) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({ withCredentials: true });
    return next.handle(request).pipe(
      catchError(
        (error) => {
          if(error instanceof HttpErrorResponse && !request.url.includes(TOKEN_HEADER) && error.status === 401){
            return this.handler401Error(request, next);
          }
          if(error instanceof HttpErrorResponse && error.status === 403){
            return this.handler403Error(request, next);
          }
          return throwError(() => error);
        }
      )
    );
  }

  private handler401Error(request: HttpRequest<any>, next: HttpHandler) {
    this.eventLogout();
    return next.handle(request);
  }

  private handler403Error(request: HttpRequest<any>, next: HttpHandler) {
    this.eventLogout();
    return next.handle(request);
  }

  private eventLogout() {
    if(this.storage.isLogged()) {
      this.storage.clean();
      window.location.reload();
    }
  }

}