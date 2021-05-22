import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/model/movie';
import { MovieService } from 'src/app/services/movie-service/movie.service';

@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

  movieId: number;
  currentMovie: Movie;
  userList: string[][];
  errorMessage: string;

  constructor(private movieService: MovieService, private route: ActivatedRoute) { 
    this.currentMovie = JSON.parse(localStorage.getItem('currentMovie'));
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      if(params.has('id')) {
        this.movieId = Number.parseInt(params.get('id'));
        this.findUsersOfMovie();
      }
    });
  }

  findUsersOfMovie() {
    this.movieService.findUsersOfMovie(this.movieId).subscribe(data => {
      this.userList = new Array<Array<string>>();
      for(var i=0;i<data.length;i=i+3){
        let temp= new Array();
        temp.push(data[i]);
        temp.push(data[i+1]);
        temp.push(data[i+2]);
        this.userList.push(temp);
      }
    },err=>{
      this.errorMessage = "There are no ratings for this movie currently!";
    });
  }
}
