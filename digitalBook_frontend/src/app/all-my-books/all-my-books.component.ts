import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-all-my-books',
  templateUrl: './all-my-books.component.html',
  styleUrls: ['./all-my-books.component.css']
})
export class AllMyBooksComponent implements OnInit {

  isPresent = false;
  bookList:any[] = [];

  book: any = {
    bookTitle: '',
    createdOn: '',
    publisher: '',
    price: '',
    active:'',
    id:''

  };
  
  authorId:any ='';


  constructor(private token:TokenStorageService,private userService: UserService,private snak: MatSnackBar,private router: Router) { }


  ngOnInit(): void {
    this.authorId = this.token.getUser().id;
    this.userService.getAuthorBooks().subscribe(
      response => {
        this.isPresent = true;
        this.bookList = response;
        //this.snak.open("Books found", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("No Books found!! ", "OK");
      }
    )
  }

  doUpdate(bookId:any):void{
    console.log("Updating book with ID "+ bookId.value);
    this.router.navigate(['/author/books/update',{bookId:bookId}]);
  }

  

  doBlock(bookId:any){
    const block:any='Yes';
    
    this.userService.blockOrUnblockBook(this.authorId,bookId,block).subscribe(
      response => {
        console.log(response);
        this.snak.open("Book is blocked", "OK");
        this.ngOnInit();
      },
      error => {
        console.log(error);
        this.ngOnInit();
        this.snak.open("Book is blocked", "OK");
      }
    )
  }

  doUnBlock(bookId:any){
    const block:any='No';

    this.userService.blockOrUnblockBook(this.authorId,bookId,block).subscribe(
      response => {
        console.log(response);
        this.snak.open("Book is unblocked", "OK");
        this.ngOnInit();
      },
      error => {
        console.log(error);
        this.ngOnInit();
        this.snak.open("Book is unblocked", "OK");
      }
    )
  }


}
