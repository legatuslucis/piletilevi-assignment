import { Component } from '@angular/core';
import {EventService} from "../../service/event.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-ticket-validation',
  templateUrl: './ticket-validation.component.html',
  styleUrls: ['./ticket-validation.component.css']
})
export class TicketValidationComponent {

  barcode: string = "";
  placeholderWarning: string | undefined;

  constructor(private service: EventService) {}

  onSubmit(): void {
    this.service.validateTicket(this.barcode).subscribe(
      {
        next: () => this.warningMessage = "Ticket is validated",
        error: (e) => this.warningMessage = e.error
      })
  }

  setWarningPlaceholder(warning: String): void {
    this.placeholderWarning = warning;
    setTimeout(function() {
      that.placeholdeWarning = undefined;
    }, 5000);
  }
}
