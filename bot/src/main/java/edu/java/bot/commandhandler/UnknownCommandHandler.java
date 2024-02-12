package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UnknownCommandHandler extends CommandHandler {
    public UnknownCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        telegramBot.execute(new SendMessage(update.getChatId(), "Неизвестная команда"));
        log.info("Ничего не делаем");
        return true;
    }
}
