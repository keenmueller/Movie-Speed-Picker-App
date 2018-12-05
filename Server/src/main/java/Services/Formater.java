package Services;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import Handlers.DetailRequest;
import Handlers.DetailResponse;
import Handlers.ListResponse;
import Handlers.ListRequest;
import Models.GenreListModel;
import Models.GenreModel;

public class Formater {

    private GenreListModel genreList;

    public ListResponse createList(ListRequest r){
        loadJson();
        APICommunicator api = new APICommunicator();
        StringBuilder str = new StringBuilder("https://api.themoviedb.org/3/discover/movie?api_key=");

        //String key = getAPIToken();
        String key = "Put The API Key Here";
        //URL base
        str.append(key);
        str.append("&language=en-US&sort_by=popularity.desc");

        if(r.getRating() != null){
            str.append("&certification_country=US&certification=");
            str.append(r.getRating());
        }
        str.append("&include_adult=false&include_video=false&page=1");
        if(r.getGenre() != null){
            str.append("&with_genres=");
            Integer id = findGenreID(r.getGenre());
            str.append(id.toString());
        }
        if(r.getRuntime() != null){
            str.append("&with_runtime.lte=");
            str.append(r.getRuntime().toString());
        }

        String url = str.toString();
        try {
            ListResponse list = api.sendListRequest(url);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DetailResponse createDetails(DetailRequest r){
        APICommunicator api = new APICommunicator();
        StringBuilder str = new StringBuilder("https://api.themoviedb.org/3/movie/");
        str.append(r.getMovieID());
        str.append("?api_key=");
        String key = "Put API key Here";
//        String key = getAPIToken();
        str.append(key);
        str.append("&language=en-US");
        String url = str.toString();

        try{
            DetailResponse details = api.sendDetailRequest(url);
            return details;
        } catch (IOException e){
            e.printStackTrace();;
        }
        return null;
    }

    void loadJson(){
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("path to JSON file"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        genreList = gson.fromJson(reader, GenreListModel.class);
    }

    int findGenreID(String name){
        for (GenreModel g : genreList.getGenres()){
            if (g.getName().equals(name)){
                return g.getId();
            }
        }
        return -1;
    }

}
