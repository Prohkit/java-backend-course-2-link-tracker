package edu.java.bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.commandhandler.CommandHandler;
import edu.java.bot.commandhandler.HelpCommandHandler;
import edu.java.bot.commandhandler.ListCommandHandler;
import edu.java.bot.commandhandler.StartCommandHandler;
import edu.java.bot.commandhandler.TrackCommandHandler;
import edu.java.bot.commandhandler.UnknownCommandHandler;
import edu.java.bot.commandhandler.UntrackCommandHandler;
import edu.java.bot.updatewrapper.UpdateWrapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotUpdateHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    private final TelegramBot telegramBot;

    private CommandHandler commandHandler;

    public TelegramBotUpdateHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void handle(UpdateWrapper update) {
        commandHandler.handleCommand(update);
    }

    @PostConstruct
    void init() {
        initChainCommandHanlders();
    }

    private void initChainCommandHanlders() {
        commandHandler = CommandHandler.link(
            new StartCommandHandler(telegramBot),
            new ListCommandHandler(telegramBot),
            new TrackCommandHandler(telegramBot),
            new UntrackCommandHandler(telegramBot),
            new HelpCommandHandler(telegramBot),
            new UnknownCommandHandler(telegramBot)
        );
    }
}
