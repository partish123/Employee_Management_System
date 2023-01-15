import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import {MatRadioModule} from '@angular/material/radio';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {

  isUpdated = false;
  

  updateuser: any = {  
    firstname:'',
    lastname:'',
    email: '',
    role:''
  };

  userId: any | null = '';
  id: any | null = '';

  constructor(private route: ActivatedRoute, private userService: UserService, private snak: MatSnackBar, private token: TokenStorageService) { }



  flag = false;

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get('userId');
    this.id = this.token.getUser().id;
    console.log('User id to update is  ' + this.userId);
    console.log('User id is  ' + this.id);
  }

  doUpdateForm() {
   
    console.log("try to save form");
    console.log("DATA ", this.updateuser);

    if (this.updateuser.firstname == '' || this.updateuser.lastname == '' || this.updateuser.email == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    const { firstname,email, lastname, role } = this.updateuser;
    let roles = [];
    roles.push(role)
    console.log(roles);
    this.flag = true;

    this.userService.updateUser(firstname,lastname,email,roles,this.userId).subscribe(
      response => {
        console.log(response);
        this.flag = false;
        this.isUpdated = true;
        this.snak.open("Updated user details Successfully", "OK");
        this.updateuser = '';

      },
      error => {
        console.log(error);
        this.flag = false;
        this.snak.open("ERROR!! ", "OK")
      }
    )

  }

}
