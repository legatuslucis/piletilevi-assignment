import {Component, ViewChild} from '@angular/core';
import {HttpService} from "./service/http.service";
import {EventsStatListComponent} from "./components/events-stat-list/events-stat-list.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Tickets';
  @ViewChild("eventStats") stats!: EventsStatListComponent;

  constructor() {}

  updateEventStats() {
    this.stats.fetchEvents();
  }
}
