import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { RouterModule } from "@angular/router";
import { BoardAdminComponent } from "../components/board-admin/board-admin.component";

@NgModule({
    declarations: [
        BoardAdminComponent
        
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
        RouterModule.forChild([
            { path: '', component: BoardAdminComponent}          
        ])

    ],
    exports: [RouterModule]
})
export class BoardAdminModule { }