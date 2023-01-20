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

  
  jobList:any[] = [];

  job: any = {
    id: '',
    name: '',
    jobCreatedDate: '',
    jobUpdatedDate: '',
    starttime:'',
    endtime:'',
    profitValue:'',
    role:'',
    status:'',
    employeeId:'',
    jobStartTime:'',
    isEdit: false,
    isPresent: false
  };


  updatejob: any = {  
    name:'',
    starttime:'',
    endtime: '',
    profitValue:'',
    role:''
  };

  userRole: any | undefined;
  private roles: string[] = [];
  showAdmin: boolean= false;
  currentUser: any;

  constructor(private userService: UserService,private snak: MatSnackBar,private router: Router,private token: TokenStorageService) { }

  ngOnInit(): void {
    
    const user = this.token.getUser();
    this.roles = user.roles;
    this.showAdmin = this.roles.includes('ROLE_ADMIN');


    this.userService.getAllJobs().subscribe(
      response => {
        
        console.log(response.body);
        this.jobList = response.body;

        // this.jobList.forEach(element => {
        //   console.log(element.roles[0].name);
          
        // });
        
        this.snak.open("Available Jobs fetched", "OK");
      },
      err => {
        console.log(err.error.message);
        this.snak.open("No Jobs found!! ", "OK");
      }
    )
  }



  doAddJob():void{
    console.log("Adding job details");
    this.router.navigate(['add']);
  }


  doDelete(jobId:any){
    const block:any='Yes';
    console.log("Deleting job details with job ID "+ jobId.value);

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


  updateUserDetails(jobId:any) {
    debugger;
    console.log("try to save form");
    console.log("DATA ", this.job);

    if (this.updatejob.name == '' || this.updatejob.starttime == '' || this.updatejob.endtime == '' || this.updatejob.profitValue == '' || this.updatejob.role == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    const {name,starttime, endtime, profitValue, role} = this.updatejob;
   
    

    this.userService.updateJob(name,starttime,endtime,profitValue,role,jobId).subscribe(
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
    item.isPresent = true;

    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is '+this.currentUser.roles[0]);

    let userid:any= this.currentUser.id;
    let jobid:any= item.id;
    let role:any = this.currentUser.roles[0];
    let status:string='Allocate';


    this.userService.allocateJob(userid,jobid,role,status).subscribe(
      response => {
        console.log(response);   
        this.snak.open('Job allocated', "OK");
        
      },
      err => {
        console.log(err.error.message);
        // this.snak.open('Job allocated', "OK")
      }
    )

    
    
    
  }


  onAbort(job: any) {
    // this.jobList.forEach(element => {
    //   element.isPresent = false;
    // });
    // item.isPresent = true;
  
    job.isPresent = false;

    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is '+this.currentUser.roles[0]);

    let userid:any= this.currentUser.id;
    let jobid:any= job.id;
    let role:any = this.currentUser.roles[0];
    let status:string='Abort';


    this.userService.abortJob(userid,jobid,role,status).subscribe(
      response => {
        console.log(response);   
        this.snak.open('Job aborted', "OK");
        
      },
      err => {
        console.log(err.error.message);
        // this.snak.open('Job aborted', "OK")
      }
    )

    

    

    
  }

  onComplete(job:any){
    this.currentUser = this.token.getUser();
    console.log(this.currentUser.id);

    console.log('Assigned role is '+this.currentUser.roles[0]);

    let userid:any= this.currentUser.id;
    let jobid:any= job.id;
    let role:any = this.currentUser.roles[0];
    let status:string='Complete';


    this.userService.completeJob(userid,jobid,role,status).subscribe(
      response => {
        console.log(response);   
        this.snak.open('Job completed', "OK");
        setTimeout(function () {
          window.location.reload();
        }, 3000);
        
      },
      err => {
        console.log(err.error.message);
        // this.snak.open('Job completed', "OK");
        setTimeout(function () {
          window.location.reload();
        }, 3000);
      }
    )

  }






}

