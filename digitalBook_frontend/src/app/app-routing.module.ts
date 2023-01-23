import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { BoardAdminComponent } from './board-admin/board-admin.component';

import { BoardAuthorComponent } from './board-author/board-author.component';

import { AlertPromise } from 'selenium-webdriver';

import { UpdatebookComponent } from './updatebook/updatebook.component';

import { JobComponent } from './job/job.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'jobs', component: JobComponent },
  { path: 'searchbook/author/:title', component: BoardAuthorComponent }, 
  { path: 'author', component: BoardAuthorComponent }, 
  { path: 'add', component: UpdatebookComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
