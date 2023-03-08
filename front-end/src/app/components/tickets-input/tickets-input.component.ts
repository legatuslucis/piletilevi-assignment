import {Component, EventEmitter, Output} from '@angular/core';
import {NewTicket} from "../../model/new-ticket";
import {EventService} from "../../service/event.service";

@Component({
  selector: 'app-tickets-input',
  templateUrl: './tickets-input.component.html',
  styleUrls: ['./tickets-input.component.css']
})
export class TicketsInputComponent {
  csvFile : File | undefined;
  warningMessage : string | undefined;
  @Output("update-events") updateEvent = new EventEmitter<void>();

  constructor(private service: EventService) {
  }

  validateExtension(file: File): boolean {
    let fileExtension = file.name.split(".").pop();
    return fileExtension === "csv";
  }

  onFileSelected(event: any): void {
    if (this.validateExtension(event.target.files[0])) {
      this.csvFile = event.target.files[0];
      this.warningMessage = undefined;
    } else {
      this.warningMessage = "Invalid file format, please select a .csv file";
    }
  }

  onSubmit(): void {
    let parsedCsv: NewTicket[] = [];
    if (this.csvFile === undefined) {
      this.warningMessage = "Please select a .csv file";
    } else {
      let fileReader = new FileReader();
      fileReader.readAsText(this.csvFile)
      fileReader.onload = () => {
        try {
          parsedCsv = this.parseCsv(fileReader.result!.toString());
        } catch (e) {
          this.warningMessage = "File content is invalid";
        }
      }

      this.service.loadNewTickets(parsedCsv).subscribe({
        next: () => {
          this.warningMessage = "Success";
          this.updateEvent.emit();
          this.csvFile = undefined;
        },
        error: (e) => this.warningMessage = e.error
      });
    }
  }

  parseCsv(csv: String): NewTicket[] {
    let newTickets: NewTicket[] = [];
    let lines = csv.split("/n").slice(1);
    lines.forEach((line) => {
        let columns = line.split(",");
        let newTicket: NewTicket = {
          eventId: BigInt(columns[0]),
          validationCode: columns[1]
        };
        newTickets.push(newTicket);
    }
    )
    return newTickets;
  }
}
