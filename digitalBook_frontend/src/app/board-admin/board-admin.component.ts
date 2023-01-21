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
  sortedUserList:any[]=[];

  user: any = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    roles:[],
    isEdit: false
  };

  // employee: any = {
  //   // id: '',
  //   // firstname: '',
  //   // lastname: '',
  //   // email: '',
  //   salary:''
  // };

  updateuser: any = {  
    firstname:'',
    lastname:'',
    email: '',
    role:''
  };

  userRole: any | undefined;
  employeeList:any[] = [];
  salary:any[]=[];

  constructor(private userService: UserService,private snak: MatSnackBar,private router: Router) { }

  ngOnInit(): void {
    this.content= "Admin Board";
    this.userService.getAllUsers().subscribe(
      response => {
        this.isPresent = true;
        console.log(response);
        this.userList = response;

        this.userList.forEach(element => {
          console.log(element.roles[0].name);

          if(element.roles[0].name !== 'ROLE_ADMIN'){
            this.sortedUserList.push(element);
          }
          
        });
        
        this.snak.open("Users found", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("No Users found!! ", "OK");
      }
    )

    this.userService.getAllEmployees().subscribe(
      response => {
        this.isPresent = true;
        console.log(response.body);
        this.employeeList = response.body;    
        this.employeeList.forEach(element => {
          console.log(element.salary);
          this.salary.push(element.salary);
          console.log(this.salary);
          console.log(this.salary.length);
          
        });

        this.snak.open("Employees found", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("No employees found!! ", "OK");
      }
    )


  }



  doUpdate(userId:any):void{
    console.log("Updating user details with ID "+ userId.value);
    this.router.navigate(['update',{userId:userId}]);
  }


  doDelete(userId:any){
    const block:any='Yes';
    console.log("Deleting user details with user ID "+ userId.value);

    this.userService.deleteUser(userId).subscribe(
      response => {
        this.isPresent = true;
        console.log(response.body);
        
        this.snak.open("User deleted", "OK");
      },
      error => {
        console.log(error);
        this.snak.open("User not deleted!! ", "OK");
      }
    )

   window.location.reload();

  }


  onEdit(item: any) {
    this.userList.forEach(element => {
      element.isEdit = false;
    });
    item.isEdit = true;
    this.updateuser.firstname = item.firstname;
    this.updateuser.lastname = item.lastname;
    this.updateuser.email = item.email;
    
  }


  updateUserDetails(userId:any) {
    debugger;
    console.log("try to save form");
    console.log("DATA ", this.user);

    if (this.updateuser.firstname == '' || this.updateuser.lastname == '' || this.updateuser.email == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    const {firstname,email, lastname, role } = this.updateuser;
    let roles = [];
    roles.push(role)
    console.log(roles);
    

    this.userService.updateUser(firstname,lastname,email,roles,userId).subscribe(
      response => {
        console.log(response);   
        this.snak.open("Updated user details Successfully", "OK");
        setTimeout(function () {
          window.location.reload();
        }, 4000);
      },
      error => {
        console.log(error);
        this.snak.open("ERROR!! ", "OK")
      }
    )

  }


}
