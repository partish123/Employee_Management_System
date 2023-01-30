import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, Route } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth-service/auth.service';
import { TokenStorageService } from '../token-storage/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private token: TokenStorageService, private auth: AuthService) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {

    const user = this.token.getUser();
    const roles: string[] = user.roles;

    // provides the route configuration options.   
    const { routeConfig } = route;
    // provides the path of the route.   
    const { path } = routeConfig as Route;
    if (path?.includes('admin') && user.roles.includes('ROLE_ADMIN')) {
      // if user is administrator and is trying to access admin routes, allow access.      
      return true;
    }
    if (path?.includes('add') && user.roles.includes('ROLE_ADMIN')) {
      // if user is administrator and is trying to access admin routes, allow access.      
      return true;
    }
    if (path?.includes('jobs') && this.auth.isLoggedIn() && roles.length) {

      return true;
    }
    if (path?.includes('home') && this.auth.isLoggedIn()) {

      return true;
    }
    if (path?.includes('profile') && this.auth.isLoggedIn()) {

      return true;
    }

    return false;




  }

}
