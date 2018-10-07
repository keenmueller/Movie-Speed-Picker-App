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

public class Handler implements HttpHandler{
    @Override
    public void handle(HttpExchange e) throws IOException {
        Gson gson = new Gson();
        Formater service = new Formater();
        ClientResponse response = new ClientResponse();

        if(e.getRequestMethod().toLowerCase().equals("post")) {
            Reader read = new InputStreamReader(e.getRequestBody());
            ClientRequest request = gson.fromJson(read, ClientRequest.class);

            //This is where I do the logic
            String result = service.trim(request.getRequestString());
            response.setAlteredString(result);

            read.close();
            if(response.getMessage() == null){
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
