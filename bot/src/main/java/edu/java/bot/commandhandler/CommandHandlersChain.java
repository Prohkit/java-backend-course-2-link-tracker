package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommandHandlersChain {
    private final TelegramBot telegramBot;

    private final List<CommandHandler> handlerList;

    public CommandHandlersChain(TelegramBot telegramBot, List<CommandHandler> handlerList) {
        this.telegramBot = telegramBot;
        this.handlerList = handlerList;
    }

    public void handleCommand(UpdateWrapper update) {
        boolean isCommandHandled = handlerList.stream()
            .map(handler -> handler.handleCommand(update))
            .toList()
            .contains(true);

        if (!isCommandHandled) {
            handleUnknownCommand(update);
        }
    }

    public boolean handleUnknownCommand(UpdateWrapper update) {
        telegramBot.execute(new SendMessage(update.getChatId(), "Неизвестная команда"));
        log.info("Ничего не делаем");
        return true;
    }
}
