import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RouterModule } from "@angular/router";
import { Ng2SearchPipeModule } from "ng2-search-filter";

import { AddjobComponent } from "../components/add-job/addjob.component";
import { JobComponent } from "../components/job/job.component";

@NgModule({
    declarations: [
        JobComponent
        
    ],
    imports: [
        FormsModule,
        CommonModule,
        MatButtonModule,
        MatSnackBarModule,
        MatFormFieldModule,
        MatInputModule,
        MatProgressSpinnerModule,
        MatRadioModule,
        Ng2SearchPipeModule,
        RouterModule.forChild([
            { path: '', component: JobComponent }
            
        ])

    ],
    exports: [RouterModule]
})
export class JobModule { }