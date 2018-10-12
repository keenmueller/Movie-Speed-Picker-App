package com.example.keenan.moviespeedpicker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.keenan.moviespeedpicker.Models.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView movieRec;
    private Button startOver;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        startOver = (Button) findViewById(R.id.button_search);
        movieRec = (RecyclerView) findViewById(R.id.movieListView);
        movieRec.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<MovieModel> full = (ArrayList) MovieResults.getInstance().getMovies();
        updateMovieUI(full);
    }

    void updateMovieUI(ArrayList<MovieModel> items) {
        movieAdapter = new MovieAdapter(this, items);
        movieRec.setAdapter(movieAdapter);
    }


    public class MovieHolder extends RecyclerView.ViewHolder {

        private TextView score;
        private TextView title;
        private LinearLayout detailsButton;
        private MovieModel fullInfo;
        private Double vote_avg;
        private String name;

        public MovieHolder(View itemView) {
            super(itemView);
            score = (TextView) itemView.findViewById(R.id.rec_score);
            title = (TextView) itemView.findViewById(R.id.rec_title);
            detailsButton = (LinearLayout) itemView.findViewById(R.id.movie_details);
        }

        void bind(Double vote_avg, String name, final MovieModel fullInfo) {
            this.vote_avg = vote_avg;
            this.name = name;
            this.fullInfo = fullInfo;

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Movie Selected", Toast.LENGTH_SHORT).show();
                    //MovieResults.getInstance().s
                    startActivity(DetailActivity.class);
                }
            });
        }
    }

    public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private ArrayList<MovieModel> all_movies;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, ArrayList<MovieModel> all_movies) {
            this.all_movies = all_movies;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.movie_layout, parent, false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            MovieModel current = all_movies.get(position);
            Double votes = current.getVote_average();
            String title = current.getTitle();
            holder.bind(votes, title, current);
        }

        @Override
        public int getItemCount() {
            return all_movies.size();
        }
    }

    void startActivity(Class aClass) {
        Intent intent = new Intent(getApplicationContext(), aClass);
        startActivity(intent);
    }
}
