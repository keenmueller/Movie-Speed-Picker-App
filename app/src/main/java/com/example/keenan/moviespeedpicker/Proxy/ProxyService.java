package com.example.keenan.moviespeedpicker.Proxy;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProxyService {
    public ListResponse getList(ListRequest r, String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(false);

        conn.connect();

        Gson gson = new Gson();
        String gsonRequest = gson.toJson(r);

        // Get the output stream containing the HTTP request body
        OutputStream reqBody = conn.getOutputStream();
        // Write the JSON data to the request body
        writeString(gsonRequest, reqBody);
        // Close the request body output stream, indicating that the
        // request is complete
        reqBody.close();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream resBody = conn.getInputStream();
            String response = readString(resBody);
            ListResponse listResponse = gson.fromJson(response, ListResponse.class);
            return listResponse;
        }
        else {
            // The HTTP response status code indicates an error
            // occurred, so print out the message from the HTTP response
            System.out.println("ERROR: " + conn.getResponseMessage());
//            LoginResponse loginResponse = new LoginResponse();
//            loginResponse.setMessage("ERROR: " + conn.getResponseMessage());
            return null;
        }
    }
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
