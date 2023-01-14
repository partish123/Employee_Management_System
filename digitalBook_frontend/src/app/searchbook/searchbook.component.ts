import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
export class SearchbookComponent implements OnInit {

  isFound = false;
  booklist: any[] = [];

  book: any = {
    bookTitle: '',
    authorId: '',
    publisher: '',
    category: '',
    price: '',
    active: ''

  };

  flag = false;
  role = this.token.getUser() != null && this.token.getUser().roles != null ? this.token.getUser().roles[0] : '';
  reader: any = {
    readerId: '',
    emailId: ''
  };

  constructor(private userService: UserService, private snak: MatSnackBar, private token: TokenStorageService) { }

  ngOnInit(): void {

  }

  doSubmitForm() {
    console.log("try to submit form");
    console.log("DATA ", this.book);

    if (this.book.bookTitle == '' || this.book.authorId == '' || this.book.category == '' || this.book.publisher == '' || this.book.bookTitle == null || this.book.authorId == null || this.book.category == null || this.book.publisher == null) {
      this.snak.open("Fields can not be empty or null !!", "OK");
      return;
    }

    this.flag = true;
    this.userService.searchBook(this.book).subscribe(
      response => {
        this.flag = false;
        this.isFound = true;
        this.booklist = response;
        this.snak.open("Search result found", "OK");
      },
      error => {
        console.log(error);
        this.flag = false;
        this.snak.open("Book not found!! ", "OK");
      }
    )

  }


  subscribe(bookId: string): void {

    this.reader.readerId = this.token.getUser().id;
    this.reader.emailId = this.token.getUser().email;
    this.userService.subscribeBook(this.reader, bookId).subscribe(
      data => {
        console.log(data);
        alert(data);
        this.snak.open("Book is subscribed", "OK");
        setTimeout(function () {
          window.location.reload();
        }, 5000);
      },
      err => {
        console.log(err.error.message);
        alert(err.error.message);
        setTimeout(function () {
          window.location.reload();
        }, 5000);
      }

    );

  }




}
