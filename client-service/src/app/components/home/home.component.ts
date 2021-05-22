import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { Rating } from 'src/app/model/rating';
import { User } from 'src/app/model/user';
import { MovieService } from 'src/app/services/movie-service/movie.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  movieList: Array<Movie>;
  errorMessage: string;
  infoMessage: string;
  currentUser: User;
  value: number;

  constructor(private userService: UserService, private movieService: MovieService, private router: Router) 
  {
    this.currentUser = this.userService.currentUserValue;
  }

  ngOnInit() {
    this.findAllMovies();
  }

  findAllMovies() {
    this.movieService.findAllMovies().subscribe(data => {
      this.movieList = data;
    });
  }

  selectOption(id: number) {
    //getted from event
    this.value = id;
    console.log(id);
  }

  rate(movie: Movie) {
    if(!this.currentUser){
      this.errorMessage = "You should sign in to rate this movie";
      return;
    }
    var rating = new Rating();
    rating.ratingValue = this.value;
    rating.userId = this.currentUser.id;
    rating.movie = movie;
    console.log(rating);

    this.movieService.rate(rating).subscribe(data => {
      this.infoMessage = "You have rated this movie!";
    }, err => {
      this.errorMessage = "Unexpected error occurred.";
    });
  }

  detail(movie: Movie) {
    localStorage.setItem("currentMovie", JSON.stringify(movie));
    this.router.navigate(['/movie', movie.id]);
  }

}
