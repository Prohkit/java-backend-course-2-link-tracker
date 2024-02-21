package edu.java.client;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClientsChain {

    private final List<Client> clientList;

    public ClientsChain(List<Client> clientList) {
        this.clientList = clientList;
    }

    public void getUpdateInfo(String url) {
        clientList.forEach(client -> client.getUpdateInfo(url));
    }
}
