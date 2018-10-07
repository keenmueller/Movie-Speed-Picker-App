package Services;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import Handlers.DetailResponse;
import Handlers.ListResponse;

public class APICommunicator {
    public ListResponse sendListRequest(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.connect();

            Gson gson = new Gson();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                ListResponse listResponse = gson.fromJson(respData, ListResponse.class);
                return listResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                ListResponse listResponse = null;
                return listResponse;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
    }

    public DetailResponse sendDetailRequest(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(false);
            conn.connect();

            Gson gson = new Gson();

            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = conn.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                DetailResponse detailResponse = gson.fromJson(respData, DetailResponse.class);
                return detailResponse;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                DetailResponse detailResponse = null;
                return detailResponse;
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;
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
