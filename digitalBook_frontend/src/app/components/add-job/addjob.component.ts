import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { TokenStorageService } from '../../_services/token-storage/token-storage.service';
import { UserService } from '../../_services/user-service/user.service';
import {MatRadioModule} from '@angular/material/radio';

@Component({
  selector: 'app-addjob',
  templateUrl: './addjob.component.html',
  styleUrls: ['./addjob.component.css']
})
export class AddjobComponent implements OnInit {

  isUpdated = false;
 

  updateuser: any = {  
    name:'',
    starttime:'',
    endtime: '',
    profitValue:'',
    role:''
  };

  userId: any | null = '';
  id: any | null = '';

  constructor(private route: ActivatedRoute, private userService: UserService, private snak: MatSnackBar, private token: TokenStorageService,private router:Router) { }

  // favoriteFruit: string | undefined;
  // fruits: string[] = ['Developer', 'Tester', 'Analyst'];

  flag = false;

  ngOnInit(): void {
    // this.userId = this.route.snapshot.paramMap.get('userId');
    this.id = this.token.getUser().id;
    // console.log('User id to update is  ' + this.userId);
    console.log('User id is  ' + this.id);
  }

  doUpdateForm() {
   
    console.log("try to save form");
    console.log("DATA ", this.updateuser);

    if (this.updateuser.firstname == '' || this.updateuser.lastname == '' || this.updateuser.email == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    const { name,starttime, endtime, profitValue,role } = this.updateuser;
    let roles = [];
    roles.push(role)
    console.log(roles);
    this.flag = true;

    this.userService.addJob(name,starttime,endtime,profitValue,role).subscribe(
      response => {
        console.log(response);
        this.flag = false;
        this.isUpdated = true;
        this.snak.open("Added job details Successfully", "OK");
        this.updateuser = '';
        this.router.navigate(['jobs']);

      },
      err => {
        console.log(err.error.message);
        this.flag = false;
        this.snak.open(err.error.message, "OK")
      }
    )

  }

}
