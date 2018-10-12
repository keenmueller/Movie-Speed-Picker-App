package Handlers;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import Services.Formater;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

public class DetailHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange e) throws IOException {
        Gson gson = new Gson();
        Formater service = new Formater();
        DetailResponse response = new DetailResponse();

        if(e.getRequestMethod().toLowerCase().equals("post")) {
            Reader read = new InputStreamReader(e.getRequestBody());
            DetailRequest request = gson.fromJson(read, DetailRequest.class);

            //This is where I do the logic
            response = service.createDetails(request);

            read.close();
            if(response != null){
                e.sendResponseHeaders(HTTP_OK, 0);
            }else{
                e.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            }
        }
        String gsonResponse = gson.toJson(response);
        PrintWriter pw = new PrintWriter(e.getResponseBody());
        pw.write(gsonResponse);
        pw.close();
    }
}
