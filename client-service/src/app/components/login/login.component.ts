import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../model/user';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(private userService: UserService, private router: Router) { 
  }

  ngOnInit(): void {
    if(this.userService.currentUserValue){
      this.router.navigate(['/home']);
      return;
    }
  }

  login(){
    this.userService.login(this.user).subscribe(data => {
      this.router.navigate(['/profile']);
    },err=>{
      this.errorMessage = "Username or password is incorrect";
    })
  }

}
