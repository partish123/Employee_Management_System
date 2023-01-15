import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {
  content?: string;

  isPresent = false;
  userList:any[] = [];

  user: any = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    salary:''
  };

  constructor(private userService: UserService,private snak: MatSnackBar,private router: Router) { }

  ngOnInit(): void {
    this.content= "Admin Board";
    this.userService.getAllUsers().subscribe(
      response => {
        this.isPresent = true;
        console.log(response.body);
        this.userList = response.body;
        this.snak.open("Users found", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("No Users found!! ", "OK");
      }
    )
  }

  // onSubmit(): void {
  //   const { username, email, password, role } = this.form;
  //   let roles = [];
  //   roles.push(role)
  //   console.log(roles);
  //   this.authService.register(username, email, password, roles).subscribe(
  //     data => {
  //       console.log(data);
  //       this.isSuccessful = true;
  //       this.isSignUpFailed = false;
  //     },
  //     err => {
  //       this.errorMessage = err.error.message;
  //       this.isSignUpFailed = true;
  //       console.log(err.error.message);
  //     }
  //   );
  // }

  doUpdate(userId:any):void{
    console.log("Updating user details with ID "+ userId.value);
    this.router.navigate(['update',{userId:userId}]);
  }


  doDelete(id:any){
    const block:any='Yes';
    
    // this.userService.blockOrUnblockBook(this.authorId,bookId,block).subscribe(
    //   response => {
    //     console.log(response);
    //     this.snak.open("Book is blocked", "OK");
    //     this.ngOnInit();
    //   },
    //   error => {
    //     console.log(error);
    //     this.ngOnInit();
    //     this.snak.open("Book is blocked", "OK");
    //   }
    // )
  }


}
