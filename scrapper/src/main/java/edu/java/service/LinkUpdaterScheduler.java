package edu.java.service;

import edu.java.client.ClientsChain;
import edu.java.client.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LinkUpdaterScheduler {

    private final ClientsChain chain;

    public LinkUpdaterScheduler(ClientsChain chain) {
        this.chain = chain;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        HttpClient client = chain.getClient();
        client.getUpdateInfo("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c");
        log.info("start update");
    }
}
