import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Rating } from 'src/app/model/rating';
import { User } from 'src/app/model/user';
import { MovieService } from 'src/app/services/movie-service/movie.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: User;
  ratingList: Array<Rating>;

  constructor(private userService: UserService, private movieService: MovieService,
    private router: Router) {
      this.currentUser = this.userService.currentUserValue;
    }

  ngOnInit() {
    if(!this.currentUser){
      this.router.navigate(['/login']);
      return;
    }
    this.findRatingsOfUser();
  }

  findRatingsOfUser() {
    this.movieService.findRatingsOfUser(this.currentUser.id).subscribe(data => {
      this.ratingList = data;
    });
  }

  logout(){
    this.userService.logout();
    this.router.navigate(['/login']);
    // this.userService.logout().subscribe(data => {
    //   this.router.navigate(['/login']);
    // });
  }

}
