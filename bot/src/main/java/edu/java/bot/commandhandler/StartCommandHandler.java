package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartCommandHandler extends CommandHandler {
    public StartCommandHandler(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        command = "/start";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), "Зарегистрирован новый пользователь"));
            log.info("Запись нового пользователя в БД");
            return true;
        }
        return handleNext(update);
    }
}
