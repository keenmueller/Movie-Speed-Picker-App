package com.example.keenan.moviespeedpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.keenan.moviespeedpicker.Models.MovieModel;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        System.out.println("Hello!");
        List<MovieModel> test = MovieResults.getInstance().getMovies();
        System.out.println("Results empty?: " + test.isEmpty());
    }
}
