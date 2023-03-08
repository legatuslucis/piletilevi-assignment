import {Component, EventEmitter, Output} from '@angular/core';
import {NewTicket} from "../../model/new-ticket";
import {HttpService} from "../../service/http.service";

@Component({
  selector: 'app-tickets-input',
  templateUrl: './tickets-input.component.html',
  styleUrls: ['./tickets-input.component.css']
})
export class TicketsInputComponent {
  warning : string | undefined;
  @Output("change") updateEvent = new EventEmitter<void>();

  constructor(private service: HttpService) {}

  validateExtension(file: File): boolean {
    let fileExtension = file.name.split(".").pop();
    return fileExtension === "csv";
  }

  onFileSelected(event: any): void {
    if (this.validateExtension(event.target.files[0])) {
      this.submitFile(event.target.files[0]);
    } else {
      this.setWarning("Invalid file format");
    }
  }

  submitFile(file: File): void {
    let parsedCsv: NewTicket[] = [];
    let fileReader = new FileReader();
    fileReader.readAsText(file);
    fileReader.onload = () => {
      try {
        parsedCsv = this.parseCsv(fileReader.result!.toString());
        this.loadTickets(parsedCsv);
      } catch (e) {
        this.setWarning("Invalid Content");
      }
    }
  }

  loadTickets(tickets: NewTicket[]) {
    this.setWarning("Processing")
    this.service.loadNewTickets(tickets).subscribe({
      next: () => {
        this.updateEvent.emit();
        this.setWarning("Success")
      },
      error: (e) => {
        this.setWarning(e.error)
      }
    });
  }

  parseCsv(csv: String): NewTicket[] {
    let newTickets: NewTicket[] = [];
    let lines = csv.split(/\r?\n/);
    lines.splice(1).forEach((line) => {
        let columns = line.split(",");
        let newTicket: NewTicket = {
          eventId: parseInt(columns[0]),
          validationCode: columns[1],
          ticketStatusId: parseInt(columns[2])
        };
        newTickets.push(newTicket);
    }
    )
    return newTickets;
  }

  setWarning(message: string): void {
    this.warning=message;
    setTimeout( () => {
      this.warning=undefined
    }, 2000);
  }
}
