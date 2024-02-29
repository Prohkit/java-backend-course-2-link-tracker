package edu.java.service;

import edu.java.client.ClientsChain;
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

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        //chain.getUpdateInfo("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c-c");
        //chain.getUpdateInfo("https://github.com/Prohkit/java-backend-course-2-link-tracker");
        log.info("start update");
    }
}
