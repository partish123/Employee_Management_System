import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})
export class CreatebookComponent implements OnInit {
  isCreated = false;

  createbook: any = {
    title: '',
    category: '',
    image: '',
    price: '',
    publisher: '',
    active: '',
    content: ''
  };
  constructor(private userService: UserService, private snak: MatSnackBar, private tokenStorage: TokenStorageService) { }

  flag = false;

  ngOnInit(): void {
  }

  doCreateForm() {
    console.log("try to save form");
    console.log("DATA ", this.createbook);

    if (this.createbook.title == '' || this.createbook.price == '' || this.createbook.category == '' || this.createbook.publisher == '' || this.createbook.active == '' || this.createbook.content == '') {
      this.snak.open("Fields can not be empty !!", "OK");
      return;
    }

    this.flag = true;

    this.userService.createBook(this.createbook).subscribe(
      response => {
        console.log(response);
        this.flag = false;
        this.isCreated = true;
        this.snak.open("Saved book Successfully", "OK");
        this.createbook = '';
      },
      error => {
        console.log(error);
        this.flag = false;
        this.snak.open("ERROR!! ", "OK")
      }
    )

  }


}
