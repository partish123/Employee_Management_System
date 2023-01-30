import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { ProfileComponent } from './components/profile/profile.component';

import { BoardAdminComponent } from './components/board-admin/board-admin.component';
import { AddjobComponent } from './components/add-job/addjob.component';

import { JobComponent } from './components/job/job.component';
import { AuthGuard } from './_services/auth-guard/auth.guard';
import { SigninComponent } from './components/signin/signin.component';
import { SignupComponent } from './components/signup/signup.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'signIn', component: SigninComponent },
  { path: 'signUp', component: SignupComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  // { path: 'admin', component: BoardAdminComponent ,canActivate: [AuthGuard] },

  { path: 'jobs', loadChildren: () => import('./modules/job.module').then(m => m.JobModule), canActivate: [AuthGuard] },
  { path: 'admin', loadChildren: () => import('./modules/board-admin.module').then(u => u.BoardAdminModule), canActivate: [AuthGuard] },


  //{ path: 'admin', loadChildren: './modules/board-admin.module#BoardAdminModule', canActivate: [AuthGuard] },
  // { path: 'add', loadChildren:'./modules/job-add.module#JobAddModule', canActivate: [AuthGuard]},
  // { path: 'jobs', loadChildren:'./modules/job-add.module#JobAddModule', canActivate: [AuthGuard]},
  { path: 'add', component: AddjobComponent, canActivate: [AuthGuard] },
  //{ path: 'jobs', component: JobComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'signIn', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
