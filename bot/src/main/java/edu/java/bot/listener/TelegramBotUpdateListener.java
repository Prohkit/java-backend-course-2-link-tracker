package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.updatewrapper.UpdateWrapper;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotUpdateListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    private final TelegramBot telegramBot;

    private final TelegramBotUpdateHandler telegramBotUpdateHandler;

    public TelegramBotUpdateListener(TelegramBot telegramBot, TelegramBotUpdateHandler telegramBotUpdateHandler) {
        this.telegramBot = telegramBot;
        this.telegramBotUpdateHandler = telegramBotUpdateHandler;
    }

    @PostConstruct
    void init() {
        telegramBot.setUpdatesListener(this);
        telegramBot.execute(new SetMyCommands(
            new BotCommand("/start", "Зарегистрировать пользователя"),
            new BotCommand("/help", "Вывести окно с командами"),
            new BotCommand("/list", "Показать список отслеживаемых ссылок"),
            new BotCommand("/track", "Начать отслеживание ссылки"),
            new BotCommand("/untrack", "Прекратить отслеживание ссылки")));
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            UpdateWrapper updateWrapper = new UpdateWrapper(update);
            telegramBotUpdateHandler.handle(updateWrapper);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
