import {Component, EventEmitter, Output} from '@angular/core';
import {HttpService} from "../../service/http.service";

@Component({
  selector: 'app-ticket-validation',
  templateUrl: './ticket-validation.component.html',
  styleUrls: ['./ticket-validation.component.css']
})
export class TicketValidationComponent {

  barcode: string = "";
  warning: string | undefined;

  @Output("change") updateEvent = new EventEmitter<void>();

  constructor(private service: HttpService) {}

  onSubmit(): void {
    this.service.validateTicket(this.barcode).subscribe(
      {
        next: () => {
          this.setWarningLabel("Success")
          this.updateEvent.emit();
        },
        error: (e) => this.setWarningLabel(e.error)
      })
    this.barcode = "";
  }

  setWarningLabel(warning: string): void {
    this.warning = warning;
    setTimeout( () => this.warning = undefined, 4000)
  }
}
