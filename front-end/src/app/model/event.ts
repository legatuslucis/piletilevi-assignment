export interface Event {
  id: bigint,
  title: string,
  location: string;
  price: number;
  date: Date;
  type: string;
  soldTickets: number;
  validatedTickets: number;
  obtainableTickets: number;
}
