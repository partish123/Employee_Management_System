import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  roles: string[] = [];
  isLoggedIn = false;
  showManagerBoard = false;
  showAuthorBoard = false;
  showReaderBoard = false;

  showAdminBoard = false;
  showDeveloperBoard = false;
  showAnalystBoard = false;
  showTesterBoard = false;

  username?: string;
  title = 'Employee Management Application';

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;
      console.log('Roles are as follows'+user);

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      // this.showManagerBoard = this.roles.includes('ROLE_MANAGER');
      this.showDeveloperBoard = this.roles.includes('ROLE_DEVELOPER');
      this.showAnalystBoard = this.roles.includes('ROLE_ANALYST');
      this.showTesterBoard = this.roles.includes('ROLE_TESTER');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
