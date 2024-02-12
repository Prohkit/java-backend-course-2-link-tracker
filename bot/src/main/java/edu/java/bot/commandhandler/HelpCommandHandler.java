package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelpCommandHandler extends CommandHandler {
    private Properties properties;

    public HelpCommandHandler(TelegramBot telegramBot) {
        try {
            properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(
                new ClassPathResource("info.properties"),
                StandardCharsets.UTF_8
            ));
        } catch (IOException e) {
            log.error("Файл info.properties не найден", e);
        }
        this.telegramBot = telegramBot;
        command = "/help";
    }

    @Override
    public boolean handleCommand(UpdateWrapper update) {
        if (update.getCommand().equals(command)) {
            telegramBot.execute(new SendMessage(update.getChatId(), properties.getProperty("help.message")).parseMode(
                ParseMode.Markdown));
            log.info("Выдача меню помощи");
            return true;
        }
        return handleNext(update);
    }
}
