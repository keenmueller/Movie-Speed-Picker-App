import java.io.IOException;

import Proxy.DetailRequest;
import Proxy.DetailResponse;
import Proxy.ListRequest;
import Proxy.ListResponse;
import Proxy.ProxyService;

public class test {
    public static void main(String[] args){
//        ListResponse response = new ListResponse();
//
//        ListRequest list = new ListRequest();
//        list.setGenre("Drama");
//        list.setRating("R");
//        list.setRuntime(180);

        DetailRequest request = new DetailRequest();
        request.setMovieID(16);
        DetailResponse response= new DetailResponse();

        ProxyService service = new ProxyService();

        try {
            response = service.getDetails(request, "http://127.0.0.1:8081/info");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail");
        }
        System.out.println("woot");
    }
}
