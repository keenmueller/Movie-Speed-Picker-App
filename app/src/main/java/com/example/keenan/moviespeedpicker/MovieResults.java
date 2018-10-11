package com.example.keenan.moviespeedpicker;

import com.example.keenan.moviespeedpicker.Models.MovieModel;

import java.util.List;

class MovieResults {
    private List<MovieModel> movies;

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
}
