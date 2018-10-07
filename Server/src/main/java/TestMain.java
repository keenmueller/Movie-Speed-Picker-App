import java.io.IOException;

import Handlers.APIResponse;
import Services.APICommunicator;

public class TestMain {
    public static void main(String[] argv){
        String url = "https://api.themoviedb.org/3/discover/movie?" +
                "api_key=636f9ac2dcc5fc7819536543b7425b02&language=en-US&sort_by=" +
                "popularity.desc&certification_country=US&certification=R&include_adult=" +
                "false&include_video=false&page=1&primary_release_year=1994";
        APICommunicator communicator = new APICommunicator();
        try{
            System.out.println("In the try");
            APIResponse testModel = communicator.sendRequest(url);
        }
        catch(IOException e){
            System.out.println("failed");
        }
    }
}
