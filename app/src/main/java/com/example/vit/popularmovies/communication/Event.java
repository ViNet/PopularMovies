package com.example.vit.popularmovies.communication;

import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.rest.model.Trailer;

import java.util.List;

public abstract class Event {

    public Event() {
    }

    /*
       Request for loading movies list
     */
    public static class LoadMoviesEvent extends Event {

        //Optional parameters
        private String sortBy;

        public LoadMoviesEvent(String sortBy) {
            this.sortBy = sortBy;
        }

        public String getSortBy() {
            return sortBy;
        }
    }

    /*
      Response for request of loading movies list
    */
    public static class LoadedMoviesEvent extends Event {
        private List<Movie> movieList;

        public LoadedMoviesEvent(List<Movie> movieList) {
            this.movieList = movieList;
        }

        public List<Movie> getMovieList() {
            return movieList;
        }
    }

    /*
      Request for loading detailed movie information by id
    */
    public static class LoadDetailedMovieEvent extends Event {
        private int id;

        public LoadDetailedMovieEvent(int movieId) {
            this.id = movieId;
        }

        public int getId() {
            return this.id;
        }
    }

    /*
       Response of success loaded detailed movie information
     */
    public static class LoadedDetailedMovieEvent extends Event {
        private DetailedMovie detailedMovie;

        public LoadedDetailedMovieEvent(DetailedMovie detailedMovie) {
            this.detailedMovie = detailedMovie;
        }

        public DetailedMovie getDetailedMovie() {
            return this.detailedMovie;
        }
    }

    /*
        Request for loading videos for a specific movie id.
    */
    public static class LoadVideosEvent extends Event {
        private int movieId;

        public LoadVideosEvent(int id) {
            this.movieId = id;
        }

        public int getMovieId() {
            return this.movieId;
        }
    }

    /*
         Response of success loaded videos for a specific movie id
    */
    public static class LoadedVideosEvent extends Event {
        private List<Trailer> trailerList;

        public LoadedVideosEvent(List<Trailer> trailers) {
            this.trailerList = trailers;
        }

        public List<Trailer> getTrailerList() {
            return this.trailerList;
        }
    }

    /*
        (Main list of films) OnItemClick event
    */
    public static class ShowMovieDetail extends Event {
        private com.example.vit.popularmovies.rest.model.Movie movie;

        public ShowMovieDetail(Movie movie) {
            this.movie = movie;
        }

        public Movie getMovie() {
            return this.movie;
        }
    }
}
