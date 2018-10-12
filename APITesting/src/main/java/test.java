import java.io.IOException;

import Proxy.ListRequest;
import Proxy.ListResponse;
import Proxy.ProxyService;

public class test {
    public static void main(String[] args){
        ListResponse response = new ListResponse();

        ListRequest list = new ListRequest();
        list.setGenre("Drama");
        list.setRating("R");
        list.setRuntime(180);

        ProxyService service = new ProxyService();

        try {
            response = service.getList(list, "http://127.0.0.1:8081/");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail");
        }
        System.out.println("woot");
    }
}
