import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { UserService } from './_services/user.service';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { AllMyBooksComponent } from './all-my-books/all-my-books.component';
import { BoardReaderComponent } from './board-reader/board-reader.component';
import { BoardAuthorComponent } from './board-author/board-author.component';
import { ReaderBooksComponent } from './reader-books/reader-books.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    SearchbookComponent,
    CreatebookComponent,
    UpdatebookComponent,
    AllMyBooksComponent,
    BoardReaderComponent,
    BoardAuthorComponent,
    ReaderBooksComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatSnackBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule
  ],
  providers: [authInterceptorProviders,MatSnackBar,UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
