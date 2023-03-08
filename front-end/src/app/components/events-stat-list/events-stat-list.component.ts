import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {EventService} from "../../service/event.service";

@Component({
  selector: 'app-events-stat-list',
  templateUrl: './events-stat-list.component.html',
  styleUrls: ['./events-stat-list.component.css']
})
export class EventsStatListComponent {
  events: Event[] | undefined;
  dataSource!: MatTableDataSource<Event>;

  readonly displayedColumns = ["date", "title", "price", "location",
    "type", "sold_tickets", "validated_tickets", "obtainable_tickets"]
  constructor(private service: EventService) {}

  ngOnInit() {
    this.fetchEvents();
  }

  fetchEvents() {
    this.service.getEventsWithStatistics().subscribe(
      {
        next: o => {
          this.events = o;
          this.dataSource = new MatTableDataSource<Event>(this.events);
        }
      })
  }
}
