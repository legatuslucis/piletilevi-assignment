import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {NewTicket} from "../model/new-ticket";

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  constructor(private http: HttpClient) { }

  public getEventsWithStatistics(): Observable<Event[]> {
    console.log("Attempt to get events statistics")
    return this.http.get<Event[]>("/api");
  }

  public loadNewTickets(newTickets: NewTicket[]): Observable<Object> {
    console.log("Attempt to load new tickets");
    return this.http.post<NewTicket[]>("/api/load-tickets", { tickets: newTickets })
  }

  public validateTicket(validationCode: String) {
    console.log("Attempt to validate ticket")
    return this.http.put("/api", validationCode);
  }

}
