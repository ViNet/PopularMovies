package com.example.vit.popularmovies.communication;

import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Movie;
import com.example.vit.popularmovies.rest.model.Page;
import com.example.vit.popularmovies.rest.model.Trailer;

import java.util.List;
import java.util.Map;

public abstract class Event {

    public Event() {
    }

    /*
       Request for loading movies list
     */
    public static class LoadMoviesEvent extends Event {

        //Optional parameters
        private Map<String, String> options;
        // Optional parameters keys
        public static final String SORT_BY = "sort_by";
        public static final String PAGE = "page";

        public LoadMoviesEvent(Map<String, String> options) {
            this.options = options;
        }

        public Map<String, String> getOptions() {
            return options;
        }
    }

    /*
      Response for request of loading movies list
    */
    public static class LoadedMoviesEvent extends Event {
        private Page page;

        public LoadedMoviesEvent(Page page) {
            this.page = page;
        }

        public Page getPage(){
            return this.page;
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
