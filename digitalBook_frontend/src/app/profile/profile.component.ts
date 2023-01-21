import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  salary:any;

  constructor(private token: TokenStorageService,private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser);
    let id= this.currentUser.id;
    console.log(this.currentUser.id);

    this.userService.getEmployeeById(id).subscribe(
      response => {       
        console.log(response['body']);
        this.salary = response['body'].salary;
        
      },
      error => {
        console.log(error);
        
      }
    )


  }
}
