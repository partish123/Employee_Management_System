import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from '../../_services/user-service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {

 
 
  //content?: string;

 // constructor(private userService: UserService) { }

  // ngOnInit(): void {
  //   this.userService.getPublicContent().subscribe(
  //     data => {
  //       this.content = data;
  //     },
  //     err => {
  //       this.content = JSON.parse(err.error).message;
  //     }
  //   );
  // }

  constructor(private snack:MatSnackBar){}

  ngOnInit(): void {
   // window.location.reload();
  }

  btnClick(){
    console.log("Button clicked");
    this.snack.open("Hey Welcome to Employee Management Application");
    window.location.reload();
  }





}
