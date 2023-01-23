import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  form: any = {
    username: null,
    firstname:null,
    lastname:null,
    email: null,
    password: null,
  };
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router:Router,private snak: MatSnackBar) { }



  onSubmit(): void {
    const { username, firstname, lastname, email, password} = this.form;
    // let roles = [];
    // roles.push(role)
    // console.log(roles);
    this.authService.register(username, firstname, lastname, email, password).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.snak.open("Your registration is successful!!! ", "OK");
        setTimeout( () => {
          this.router.navigate(['home']);
        }, 2000);
       
     
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        console.log(err.error.message);
      }
    );
  }
}
