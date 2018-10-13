package com.example.keenan.moviespeedpicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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


import com.bumptech.glide.Glide;
import com.example.keenan.moviespeedpicker.Models.MovieModel;
import com.example.keenan.moviespeedpicker.Proxy.DetailRequest;
import com.example.keenan.moviespeedpicker.Proxy.DetailResponse;
import com.example.keenan.moviespeedpicker.Proxy.ListRequest;
import com.example.keenan.moviespeedpicker.Proxy.ListResponse;
import com.example.keenan.moviespeedpicker.Proxy.ProxyService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        private ImageView poster;
        private LinearLayout detailsButton;
        private MovieModel fullInfo;
        private Double vote_avg;
        private String name;
        private String pic;

        public MovieHolder(View itemView) {
            super(itemView);
            score = (TextView) itemView.findViewById(R.id.rec_score);
            title = (TextView) itemView.findViewById(R.id.rec_title);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            detailsButton = (LinearLayout) itemView.findViewById(R.id.movie_details);
        }

        void bind(Double vote_avg, String name, String pic, final MovieModel fullInfo) {
            this.vote_avg = vote_avg;
            this.name = name;
            this.pic = pic;
            this.fullInfo = fullInfo;

            score.setText(Double.toString(vote_avg) + " / 10");
            title.setText(name);
            String url = "https://image.tmdb.org/t/p/w200" + pic;

            Picasso.get().load(url).into(poster);

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Movie Selected", Toast.LENGTH_SHORT).show();

                    String movieID = Integer.toString(fullInfo.getId());
                    new MovieAsync().execute(movieID);
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
            String image = current.getPoster_path();
            holder.bind(votes, title, image, current);
        }

        @Override
        public int getItemCount() {
            return all_movies.size();
        }
    }

    class MovieAsync extends AsyncTask<String, Void, String> {
        private String url;

        @Override
        protected String doInBackground(String... params) {
            //url = "http://10.0.2.2:8081/info";
            url = "http://192.168.1.107:8081/info";

            DetailRequest request = new DetailRequest();
            request.setMovieID(Integer.parseInt(params[0]));

            ProxyService proxy = new ProxyService();
            try {
                DetailResponse response = proxy.getDetails(request, url);
                MovieResults.getInstance().setFull_details(response);
                return "success!";
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(DetailActivity.class);
        }
    }

    void startActivity(Class aClass) {
        Intent intent = new Intent(getApplicationContext(), aClass);
        startActivity(intent);
    }
}
