import type { DISPLAY_FORMAT, GENRE, STATUS } from "../constants/cinema.const";

export type Genre = (typeof GENRE)[keyof typeof GENRE];
export type DisplayFormat =
  (typeof DISPLAY_FORMAT)[keyof typeof DISPLAY_FORMAT];
export type Status = (typeof STATUS)[keyof typeof STATUS];

export interface Movie {
  id: number;
  name: string;
  genres: Genre[];
  duration: number;
  description: string;
  sessions: Session[];
}

export interface Session {
  id: number;
  startDate: Date;
  endDate: Date;
  duration: number;
  displayFormat: DisplayFormat;
  price: number;
  hall: Hall;
  movie: Movie;
}

export interface Seat {
  id: number;
  row: number;
  number: number;
  hall: Hall;
}

export interface Hall {
  id: number;
  name: string;
  capacity: number;
  seats: Seat[];
}

export interface Reservation {
  id: number;
  contactEmail: string;
  status: Status;
  price: number;
  seats: Seat[];
  session: Session;
}
