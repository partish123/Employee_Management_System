import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {

  isUpdated = false;


  updatebook : any ={
    bookTitle : '',
    category : '',
    image : '',
    price : '',
    publisher : '',
    active : '',
    bookcontent : '',
    
  };

  bookId: any | null = '';
  id: any |null='';

  constructor(private route:ActivatedRoute,private userService: UserService, private snak:MatSnackBar,private token: TokenStorageService) { }

  

  flag= false;

  ngOnInit(): void {
    this.bookId = this.route.snapshot.paramMap.get('bookId');
    this.id=this.token.getUser().id;
    console.log('Book id to update is  '+ this.bookId);
    console.log('Author id is  '+ this.id);
  }

  doUpdateForm(){
  console.log("try to save form");
  console.log("DATA ",this.updatebook);

  if(this.updatebook.bookTitle=='' || this.updatebook.price==''|| this.updatebook.category=='' || this.updatebook.publisher=='' || this.updatebook.active=='' || this.updatebook.bookcontent=='')
  {
    this.snak.open("Fields can not be empty !!","OK");
    return;
  }

  this.flag=true;

  this.userService.updateBook(this.updatebook,this.bookId).subscribe(
    response=>{
      console.log(response);   
      this.flag=false; 
      this.isUpdated= true;
      this.snak.open("Updated book Successfully","OK");  
      this.updatebook = '';

    },
    error=>{
      console.log(error); 
      this.flag=false;    
      this.snak.open("ERROR!! ","OK")   
    }
  )  
  
  }

}
