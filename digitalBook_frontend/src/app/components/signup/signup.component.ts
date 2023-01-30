import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
//import { UserService } from 'src/app/_services/user-service/user.service';
import { AuthService } from 'src/app/_services/auth-service/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',    
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  
  
    loading = false;
    submitted = false;
    registerForm!:FormGroup;

    isSuccessful = false;
    isSignUpFailed = false;
    errorMessage = '';
    
   

    constructor(     
        private formBuilder: FormBuilder,
        private router: Router,
        private snak: MatSnackBar,
        private authservice: AuthService,
        
    ) { 
        
    }

    ngOnInit() {      
            this.registerForm = this.formBuilder.group({
            username: ['', [Validators.required,Validators.pattern('^[a-zA-Z0-9]+$'),Validators.minLength(3),Validators.maxLength(15)]],
            firstName: ['', [Validators.required,Validators.pattern('^[a-zA-Z]+$'),Validators.minLength(3),Validators.maxLength(15)]],
            lastName: ['', [Validators.required,Validators.pattern('^[a-zA-Z]+$'),Validators.minLength(3),Validators.maxLength(15)]],          
            email: ['', [Validators.required,Validators.email]],
            password: ['', [Validators.required,Validators.minLength(6),Validators.maxLength(15),Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,20}$')]]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        console.log(this.registerForm.value.username,this.registerForm.value.firstName,this.registerForm.value.lastName,this.registerForm.value.email,this.registerForm.value.password);

        this.loading = true;
        this.authservice.register(this.registerForm.value.username,this.registerForm.value.firstName,this.registerForm.value.lastName,this.registerForm.value.email,this.registerForm.value.password)
            .pipe(first())
            .subscribe(
                data => {
                  console.log(data);
                  this.isSuccessful = true;
                  this.isSignUpFailed = false;
                  this.snak.open("Your registration is successful!!! ", "OK");
                  setTimeout( () => {
                    this.router.navigate(['signIn']);
                  }, 2000);
                    
                },
                err => {
                  this.errorMessage = err.error.message;
                  this.isSignUpFailed = true;
                  console.log(err.error.message);

                    this.loading = false;
                });


                



    }

}
