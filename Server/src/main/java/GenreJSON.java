import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

import Models.GenreListModel;
import Models.GenreModel;

class GenreJSON {
    private GenreListModel genreList;

    private static final GenreJSON ourInstance = new GenreJSON();

    static GenreJSON getInstance() {
        return ourInstance;
    }

    private GenreJSON() {
    }

    void loadJson(){
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("C:\\Users\\keena\\AndroidStudioProjects\\Movie-Speed-Picker-App\\Server\\genres.json"));
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

    public GenreListModel getGenreList() {
        return genreList;
    }

    public void setGenreList(GenreListModel genreList) {
        this.genreList = genreList;
    }
}
