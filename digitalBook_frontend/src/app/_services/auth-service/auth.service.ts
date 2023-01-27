import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../token-storage/token-storage.service';

  const AUTH_API = 'http://localhost:8085/api/user/';
// const AUTH_API: string = 'http://employee-management-lb-1792055145.us-east-1.elb.amazonaws.com/api/user/';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient,private tokenStorage:TokenStorageService) { }

   /* Should check whenther the user is Logged In */
  isLoggedIn(){
    return !!this.tokenStorage.getToken();
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'sign-in', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, firstname:string, lastname:string , email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'sign-up', {
      username,
      firstname,
      lastname,
      email,
      password,
    }, httpOptions);
  }
}
