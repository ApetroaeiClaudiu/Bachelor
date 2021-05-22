import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, BehaviorSubject, fromEventPattern} from 'rxjs';
import {map} from 'rxjs/operators';
import { User } from '../../model/user';

let API_URL = "http://localhost:8765/api/user/service/";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('currentUser'))
    );
    this.currentUser = this.currentUserSubject.asObservable();
   }

   public get currentUserValue(): User{
     return this.currentUserSubject.value;
   }

   login(user: User): Observable<any>{
     const headers = new HttpHeaders(
       user ? {
         authorization:'Basic ' + btoa(user.username + ':' + user.password)
       }:{}
     );
     console.log(headers);
     return this.http.get<any>(API_URL + "login",{headers: headers}).pipe(
       map(response => {
         if(response){
           localStorage.setItem('currentUser',JSON.stringify(response));
           this.currentUserSubject.next(response);
         }
         return response;
       })
     );
   }

   //: Observable<any>
   logout(){
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    //  return this.http.post(API_URL + "logout",{}).pipe(
    //    map(response =>{
    //      console.log(response);
    //      localStorage.removeItem('currentUser');
    //      this.currentUserSubject.next(null);
    //    })
    //  );
   }

   register(user: User): Observable<any>{
     return this.http.post(API_URL + "register", JSON.stringify(user),
     {headers: {"Content-Type":"application/json; charset=UTF-8"}});
   }
}
