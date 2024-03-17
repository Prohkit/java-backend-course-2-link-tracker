package edu.java.client;

import edu.java.domain.Link;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ClientsChain {

    private final List<Client> clientList;

    public ClientsChain(List<Client> clientList) {
        this.clientList = clientList;
    }

    public String getUpdateInfo(Link link) {
        String description = null;
        for (Client client : clientList) {
            description = client.getUpdateInfo(link);
            if (description != null) {
                break;
            }
        }
        return description;
    }
}
