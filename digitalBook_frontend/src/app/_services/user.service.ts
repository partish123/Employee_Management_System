import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

const API_URL:string = 'http://localhost:8081/api/user/';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private token: TokenStorageService) { }

  authorID: any = this.token.getUser().id;

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }


  searchBook(book:any): Observable<any>{
     return this.http.get(`${API_URL}search?title=${book.bookTitle}&category=${book.category}&author=${book.authorId}&price=${book.price}&publisher=${book.publisher}`);
  }

  createBook(createBook:any): Observable<any>{
     return this.http.post(`${API_URL}author/${this.token.getUser().id}/books`,createBook,{ responseType: 'text' });
  }

  updateBook(updatebook:any,bookID:any): Observable<any>{
    return this.http.post(`${API_URL}author/${this.token.getUser().id}/books/${bookID}`,updatebook,{ responseType: 'text' });
 }

  getAuthorBooks(): Observable<any>{
    return this.http.get(`${API_URL}search?author=${this.authorID}`);
  }

  getAuthorBooksContent(title:any): Observable<any>{
    return this.http.get(`${API_URL}search?title=${title}`);
  }

  getReaderBooksContent(title:any): Observable<any>{
    return this.http.get(`${API_URL}search?title=${title}`);
  }

  blockOrUnblockBook(authorId:any ,bookId:any,block:any): Observable<any> {
    return this.http.post(`${API_URL}author/${authorId}/books/${bookId}/block=${block}`, { responseType:'text'});
  }

  subscribeBook(reader: any, bookId: string) {
    return this.http.post(API_URL +bookId+'/subscribe', reader,{responseType: 'text'});
  }

  getReaderSubscribedBooks(emailId: any) {
    return this.http.get(`${API_URL}readers/${emailId}/books`, { responseType: 'text' });
  }

  cancelSubscribe(subscriptionId: any): Observable<any>  {
    return this.http.post(API_URL+'readers/'+ this.token.getUser().email +'/books/'+subscriptionId+'/cancel-subscription',{responseType: 'text'});
  }

}








