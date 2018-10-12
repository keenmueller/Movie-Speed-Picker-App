package com.example.keenan.moviespeedpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.keenan.moviespeedpicker.Models.GenreModel;
import com.example.keenan.moviespeedpicker.Proxy.DetailResponse;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    private ImageView poster;
    private TextView score;
    private TextView title;
    private TextView genres;
    private TextView overview;
    private TextView length;
    private TextView rating;
    private TextView year;
    private TextView language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get information
        DetailResponse movieInfo = MovieResults.getInstance().getFull_details();

        //Attaching views
        poster = (ImageView) findViewById(R.id.detail_poster);
        String path = movieInfo.getPoster_path();
        String url = "https://image.tmdb.org/t/p/w400" + path;
        Picasso.get().load(url).into(poster);

        score = (TextView) findViewById(R.id.detail_score);
        StringBuilder pop = new StringBuilder();
        pop.append(Double.toString(movieInfo.getVote_average()));
        pop.append(" / 10");
        score.setText(pop.toString());

        title = (TextView) findViewById(R.id.detail_title);
        title.setText(movieInfo.getTitle());

        StringBuilder genreList = new StringBuilder();
        for (GenreModel g : movieInfo.getGenres()){
            genreList.append(g.getName());
            genreList.append(", ");
        }
        String gList = genreList.substring(0, genreList.length() - 2);
        genres = (TextView) findViewById(R.id.detail_genres);
        genres.setText(gList);

        overview = (TextView) findViewById(R.id.synopsis);
        overview.setText(movieInfo.getOverview());

        length = (TextView) findViewById(R.id.detail_length);
        String hours = convertToHours(movieInfo.getRuntime()) + " Hours";
        length.setText(hours);

        rating = (TextView) findViewById(R.id.detail_rating);
        String usRating = MovieResults.getInstance().getSelectedMovieRating();
        rating.setText(usRating);

        year = (TextView) findViewById(R.id.detail_year);
        year.setText(movieInfo.getRelease_date());

        language = (TextView) findViewById(R.id.detail_language);
        language.setText(movieInfo.getOriginal_language());

    }

    private String convertToHours(int runtime){
        double mathReady = runtime;
        Double hours = mathReady / 60;
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(hours);
    }
}
