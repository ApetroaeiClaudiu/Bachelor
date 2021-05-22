import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Rating } from 'src/app/model/rating';

let API_URL = "http://localhost:8765/api/movie/service/";

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http: HttpClient) { }

  rate(rating: Rating): Observable<any>{
    return this.http.post(API_URL + 'rate',JSON.stringify(rating),
    {headers: {"Content-Type":"application/json; charset=UTF-8"}});
  }

  findAllMovies(): Observable<any>{
    return this.http.get(API_URL + "all",
    {headers: {"Content-Type":"application/json; charset=UTF-8"}});
  }

  findRatingsOfUser(userId: number): Observable<any> {
    return this.http.get(API_URL + "user/" + userId, 
    {headers: {"Content-Type":"application/json; charset=UTF-8"}});
  }

  findUsersOfMovie(movieId: number): Observable<any> {
    return this.http.get(API_URL + "movie/" + movieId, 
    {headers: {"Content-Type":"application/json; charset=UTF-8"}});
  }
}
