import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  content?: string;


  jobList: any[] = [];
  sortedList: any[] = [];

  job: any = {
    id: '',
    name: '',
    jobCreatedDate: '',
    jobUpdatedDate: '',
    starttime: '',
    endtime: '',
    profitValue: '',
    role: '',
    status: '',
    employeeId: '',
    jobStartTime: '',
    isEdit: false,
    isPresent: false
  };


  updatejob: any = {
    name: '',
    starttime: '',
    endtime: '',
    profitValue: '',
    role: ''
  };

  userRole: any | undefined;
  private roles: string[] = [];
  showAdmin: boolean = false;
  currentUser: any;
  empId: any;

  constructor(private userService: UserService, private snak: MatSnackBar, private router: Router, private token: TokenStorageService) { }

  ngOnInit(): void {

    const user = this.token.getUser();
    this.roles = user.roles;
    this.showAdmin = this.roles.includes('ROLE_ADMIN');
    this.empId = user.id;

    if (this.showAdmin) {
      this.userService.getAllJobs().subscribe(
        response => {

          console.log(response.body);
          this.sortedList = response.body;
          this.snak.open("Available Jobs fetched", "OK");
        },
        err => {
          console.log(err.error.message);
          this.snak.open("No Jobs found!! ", "OK");
        }
      )
    }
    else {
      this.userService.getAllJobs().subscribe(
        response => {

          console.log(response.body);
          this.jobList = response.body;

          this.jobList.forEach(element => {

            console.log(element['status']);
            if (element['status'] === 'NOT_STARTED' || element['status'] === 'IN_PROGRESS') {
              this.sortedList.push(element);
            }
            console.log(this.sortedList);
          });

          this.snak.open("Available Jobs fetched", "OK");
        },
        err => {
          console.log(err.error.message);
          this.snak.open("No Jobs found!! ", "OK");
        }
      )

    }


  }



  doAddJob(): void {
    console.log("Adding job details");
    this.router.navigate(['add']);
  }


  doDelete(jobId: any) {
    const block: any = 'Yes';
    console.log("Deleting job details with job ID " + jobId.value);

    this.userService.deleteJob(jobId).subscribe(
      response => {

        console.log(response.body);

        this.snak.open("Job deleted", "OK");
        setTimeout(function () {
          window.location.reload();
        }, 2000);
      },
      err => {
        console.log(err.error.message);
        this.snak.open("Job not deleted!! ", "OK");
      }
    )



  }


  onEdit(item: any) {
    this.jobList.forEach(element => {
      element.isEdit = false;
    });
    item.isEdit = true;
    this.updatejob.name = item.name;
    this.updatejob.starttime = item.starttime;
    this.updatejob.endtime = item.endtime;
    this.updatejob.profitValue = item.profitValue;
    this.updatejob.role = item.role;

  }


  updateUserDetails(jobId: any) {
    debugger;
    console.log("try to save form");
    console.log("DATA ", this.job);

    if (this.updatejob.name == '' || this.updatejob.starttime == '' || this.updatejob.endtime == '' || this.updatejob.profitValue == '' || this.updatejob.role == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    const { name, starttime, endtime, profitValue, role } = this.updatejob;



    this.userService.updateJob(name, starttime, endtime, profitValue, role, jobId).subscribe(
      response => {
        console.log(response);
        this.snak.open("Updated job details Successfully", "OK");
        setTimeout(function () {
          window.location.reload();
        }, 2000);
      },
      err => {
        console.log(err.error.message);
        this.snak.open(err.error.message, "OK");
      }
    )

  }


  onAllocate(item: any) {
    this.jobList.forEach(element => {
      element.isPresent = false;
    });


    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is' + this.currentUser.roles[0]);

    let userid: any = this.currentUser.id;
    let jobid: any = item.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Allocate';


    this.userService.allocateJob(userid, jobid, role, status).subscribe(
      response => {
        console.log(response);
        this.snak.open(response, "OK");
        item.isPresent = true;
        setTimeout(function () {
          window.location.reload();
        }, 2000);

      },
      err => {
        console.log(err.error);
        const myObj = JSON.parse(err.error);
        this.snak.open(myObj.message, "OK");
      }
    )





  }


  onAbort(job: any) {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is ' + this.currentUser.roles[0]);

    let userid: any = this.currentUser.id;
    let jobid: any = job.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Abort';


    this.userService.abortJob(userid, jobid, role, status).subscribe(
      response => {
        console.log(response);
        this.snak.open(response, "OK");
        job.isPresent = false;
        setTimeout(function () {
          window.location.reload();
        }, 2000);
      },
      err => {
        console.log(err.error);
        const myObj = JSON.parse(err.error);
        this.snak.open(myObj.message, "OK");
      }
    )

  }

  onComplete(job: any) {
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is ' + this.currentUser.roles[0]);

    let userid: any = this.currentUser.id;
    let jobid: any = job.id;
    let role: any = this.currentUser.roles[0];
    let status: string = 'Complete';


    this.userService.completeJob(userid, jobid, role, status).subscribe(
      response => {
        console.log(response);
        this.snak.open(response, "OK");
        setTimeout(function () {
          window.location.reload();
        }, 2000);

      },
      err => {
        console.log(err.error);
        const myObj = JSON.parse(err.error);
        this.snak.open(myObj.message, "OK");
       
      }
    )

  }






}

