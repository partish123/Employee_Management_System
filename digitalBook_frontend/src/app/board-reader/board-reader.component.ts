import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-reader',
  templateUrl: './board-reader.component.html',
  styleUrls: ['./board-reader.component.css']
})
export class BoardReaderComponent implements OnInit {

  content?:any |null='';

  bookList:any[] = [];
  
  isPresent = false;
  title: any | null;


  constructor(private token:TokenStorageService,private userService: UserService,private snak: MatSnackBar,private route:ActivatedRoute) { }


  ngOnInit(): void {
    this.title= this.route.snapshot.paramMap.get('title');

   
    this.userService.getReaderBooksContent(this.title).subscribe(
      response => {
        this.isPresent = true;
        this.bookList = response;
        for (let index = 0; index < this.bookList.length; index++) {

          const element = this.bookList[index].bookTitle;
          console.log(element);
          if(element === this.title){
              this.content = this.bookList[index].content;
              console.log(this.content);
          }
          
        }

        //this.snak.open("Books found", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("No Books found!! ", "OK");
      }
    )
  }

 



}
