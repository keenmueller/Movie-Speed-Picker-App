import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import Handlers.DetailHandler;
import Handlers.ListHandler;
import Handlers.ListRequest;
import Handlers.ListResponse;
import Services.Formater;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);
        server.createContext("/info", new DetailHandler());
        server.createContext("/", new ListHandler()); // put this context towards the end of the server class:
        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }
    public static void main(String[] args) {
        String portNumber = "8081";
        new Server().run(portNumber);
    }
//    public static void main(String[] args){
//
//        Formater formater = new Formater();
//        ListRequest request = new ListRequest();
//        request.setGenre("Drama");
//        request.setRating("R");
//        request.setRuntime(180);
//
//        ListResponse response = new ListResponse();
//
//        response = formater.createList(request);
//
//        System.out.println("hello");
//    }
}
