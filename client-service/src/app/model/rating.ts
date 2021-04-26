import { Movie } from "./movie";

export class Rating{
    id: number;
    movie: Movie;
    userId: number;
    ratingValue: number;
}