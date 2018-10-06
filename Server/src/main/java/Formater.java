import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import Handlers.APIResponse;
import Handlers.ClientRequest;
import Handlers.ClientResponse;

public class Formater {
    ClientResponse createList(ClientRequest r){
        APICommunicator api = new APICommunicator();
        StringBuilder str = new StringBuilder("https://api.themoviedb.org/3/discover/movie?api_key=");

        String key = getAPIToken();
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
            str.append(r.getGenre().toString());
        }
        if(r.getRuntime() != null){
            str.append("&with_runtime.lte=");
            str.append(r.getRuntime().toString());
        }

        String url = str.toString();
        try {
            APIResponse list = api.sendRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getAPIToken(){
        File key = new File("C:\\Users\\keena\\secret\\movieapi.txt");
        try {
            FileInputStream fileReader = new FileInputStream(key);
            BufferedInputStream bufferedReader = new BufferedInputStream(fileReader);
            Scanner s = new Scanner(bufferedReader);

            String gotKey = s.next();

            s.close();
            bufferedReader.close();
            fileReader.close();
            return gotKey;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + dictionary + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + dictionary + "'");
        }
        return null;
    }
}
