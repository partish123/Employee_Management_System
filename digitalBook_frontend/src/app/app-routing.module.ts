import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { BoardAuthorComponent } from './board-author/board-author.component';
import { BoardReaderComponent } from './board-reader/board-reader.component';
import { AlertPromise } from 'selenium-webdriver';
import { AllMyBooksComponent } from './all-my-books/all-my-books.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { ReaderBooksComponent } from './reader-books/reader-books.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'searchbook', component: SearchbookComponent },
  { path: 'createbook', component: CreatebookComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'searchbook/author/:title', component: BoardAuthorComponent }, 
  { path: 'author', component: BoardAuthorComponent }, 
  { path: 'read', component: BoardReaderComponent },
  { path: 'author/books', component: AllMyBooksComponent },
  { path: 'reader/books', component: ReaderBooksComponent },
  { path: 'author/books/update', component: UpdatebookComponent },
  { path: 'reader', component: BoardReaderComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
