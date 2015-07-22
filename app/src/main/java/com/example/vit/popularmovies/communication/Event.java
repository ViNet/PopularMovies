package com.example.vit.popularmovies.communication;

import com.example.vit.popularmovies.rest.model.DetailedMovie;
import com.example.vit.popularmovies.rest.model.Movie;

import java.util.List;

public abstract class Event {

    public Event(){}

    public static class LoadMoviesEvent extends Event{

        //Optional parameters
        private String sortBy;

        public LoadMoviesEvent(String sortBy){
            this.sortBy = sortBy;
        }

        public String getSortBy() {
            return sortBy;
        }
    }

    public static class LoadedMoviesEvent extends Event{
        private List<Movie> movieList;

        public LoadedMoviesEvent(List<Movie> movieList){
            this.movieList = movieList;
        }

        public List<Movie> getMovieList(){
            return movieList;
        }
    }

    public static class LoadDetailedMovieEvent extends Event{
        private int id;

        public LoadDetailedMovieEvent(int movieId){
            this.id = movieId;
        }

        public int getId(){
            return this.id;
        }
    }

    public static class LoadedDetailedMovieEvent extends Event{
        private DetailedMovie detailedMovie;

        public LoadedDetailedMovieEvent(DetailedMovie detailedMovie){
            this.detailedMovie = detailedMovie;
        }

        public DetailedMovie getDetailedMovie(){
            return this.detailedMovie;
        }
    }


    public static class ShowMovieDetail extends Event{
        private com.example.vit.popularmovies.rest.model.Movie movie;

        public ShowMovieDetail(Movie movie){
            this.movie = movie;
        }

        public Movie getMovie(){
            return this.movie;
        }
    }
}
