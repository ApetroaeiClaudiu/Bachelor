import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from './model/user';
import { UserService } from './services/user-service/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client-service';
  currentUser: User;

  constructor(private userService: UserService, private router: Router) {
    //Call it observable because it can be changed from other page like login.
    this.userService.currentUser.subscribe(data => {
      this.currentUser = data;
    });
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['login']);
    // this.userService.logout().subscribe(data => {
    //   this.router.navigate(['/login']);
    // });
  }
}
