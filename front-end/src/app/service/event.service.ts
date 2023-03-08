import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {NewTicket} from "../model/new-ticket";

@Injectable({
  providedIn: 'root'
})
export class EventService {
  constructor(private http: HttpClient) { }

  public getEventsWithStatistics(): Observable<Event[]> {
    return this.http.get<Event[]>("/api");
  }

  public loadNewTickets(newTickets: NewTicket[]): Observable<Object> {
    return this.http.post("/api", newTickets)
  }

  public validateTicket(validationCode: String) {
    return this.http.put("/api", validationCode);
  }

}
