package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            UpdateWrapper updateWrapper = new UpdateWrapper(update);
            if (updateWrapper.getMessageText().equals("/start")) {
                telegramBot.execute(new SendMessage(updateWrapper.getChatId(), "Добрый день!"));
            } else {
                telegramBot.execute(new SendMessage(updateWrapper.getChatId(), "Команда неизвестна."));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
