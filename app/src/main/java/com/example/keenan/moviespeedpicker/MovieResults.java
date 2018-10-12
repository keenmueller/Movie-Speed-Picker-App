package com.example.keenan.moviespeedpicker;

import com.example.keenan.moviespeedpicker.Models.MovieModel;
import com.example.keenan.moviespeedpicker.Proxy.DetailResponse;

import java.util.List;

class MovieResults {
    private List<MovieModel> movies;
    private DetailResponse full_details;
    private String selectedMovieRating;

    private static final MovieResults ourInstance = new MovieResults();

    static MovieResults getInstance() {
        return ourInstance;
    }

    private MovieResults() {
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

    public DetailResponse getFull_details() {
        return full_details;
    }

    public void setFull_details(DetailResponse full_details) {
        this.full_details = full_details;
    }

    public String getSelectedMovieRating() {
        return selectedMovieRating;
    }

    public void setSelectedMovieRating(String selectedMovieRating) {
        this.selectedMovieRating = selectedMovieRating;
    }
}
