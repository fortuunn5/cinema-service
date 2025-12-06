export const GENRE = {
  BIOGRAPHY: "биография",
  ACTION: "боевик",
  DETECTIVE: "детектив",
  DOCUMENTARY: "документальный",
  DRAMA: "драма",
  HISTORICAL: "исторический",
  COMEDY: "комедия",
  CRIME: "криминал",
  ROMANCE: "мелодрама",
  MYSTERY: "мистика",
  ANIMATION: "мультфильм",
  MUSICAL: "мюзикл",
  ADVENTURE: "приключение",
  THRILLER: "триллер",
  HORROR: "ужасы",
  SCIENCE_FICTION: "фантастика",
  FANTASY: "фэнтези",
} as const;

export const DISPLAY_FORMAT = {
  _2D: "2D",
  _3D: "3D",
} as const;

export const STATUS = {
  RESERVED: "забронировано",
  PAID: "оплачено",
  CANCELLED: "отменено",
} as const;