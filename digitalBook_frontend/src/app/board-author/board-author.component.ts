import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-author',
  templateUrl: './board-author.component.html',
  styleUrls: ['./board-author.component.css']
})
export class BoardAuthorComponent implements OnInit {

  title?:string |null='';

  bookList:any[] = [];
  content :any ='';
  isPresent = false;

  constructor(private route:ActivatedRoute,private token:TokenStorageService,private userService: UserService,private snak: MatSnackBar) { }

  ngOnInit(): void {
    this.title= this.route.snapshot.paramMap.get('title');

   
    this.userService.getAuthorBooksContent(this.title).subscribe(
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
