package edu.java.service;

import edu.java.dto.bot.LinkUpdate;

public interface NotificationSendingService {
    void send(LinkUpdate update);
}
