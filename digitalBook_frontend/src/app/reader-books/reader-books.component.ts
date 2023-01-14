import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-reader-books',
  templateUrl: './reader-books.component.html',
  styleUrls: ['./reader-books.component.css']
})
export class ReaderBooksComponent implements OnInit {

  isPresent = false;
  bookList: any[] = [];

  book: any = {
    bookTitle: '',
    createdOn: '',
    publisher: '',
    price: '',
    active: '',
    id: ''

  };

  authorId: any = '';
  emailId: any = '';
  flag= false;


  constructor(private token: TokenStorageService, private userService: UserService, private snak: MatSnackBar, private router: Router) { }


  ngOnInit(): void {
    this.emailId = this.token.getUser().email;
    console.log(this.emailId);

    this.userService.getReaderSubscribedBooks(this.emailId).subscribe(
      data => {
        this.bookList = JSON.parse(data);
        console.log(this.bookList);
        
      },
      err => {
        console.log(err.error.message);
        this.book='';
        this.snak.open("No Books are subscribed by reader","OK");  
      }
    );
  }

  

  getContent(title:any){
    this.router.navigate(['/read',{title:title}])
  }


  cancelSubscribe(subscriptionId:any){    
    this.userService.cancelSubscribe(subscriptionId).subscribe(
      data => {
        console.log(data);
        this.flag= true;
        this.snak.open("Book Subscription cancelled","OK");  
        this.ngOnInit();
      },
      err => {
        alert(console.error());
        console.log(err.error.message) ;
        this.ngOnInit();
      }

      
    );

    

  }

}
