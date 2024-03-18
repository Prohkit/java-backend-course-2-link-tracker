package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.exception.handler.ApiErrorResponse;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendMessageService {
    private final TelegramBot telegramBot;

    public SendMessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(UpdateWrapper update, String text) {
        telegramBot.execute(new SendMessage(update.getChatId(), text));
    }

    public void sendMessage(UpdateWrapper update, String text, ParseMode parseMode) {
        telegramBot.execute(new SendMessage(update.getChatId(), text).parseMode(parseMode));
    }

    public void sendApiError(Long tgChatId, ApiErrorResponse apiErrorResponse) {
        telegramBot.execute(new SendMessage(tgChatId, apiErrorResponse.getExceptionMessage()));
    }

    public void sendLinkUpdateMessage(Long tgChatId, URI url, String description) {
        String message = "По ссылке " + url.toString() + " Появилось обновление." + System.lineSeparator()
            + "Описание обновления: " + description;
        telegramBot.execute(new SendMessage(tgChatId, message));
    }
}
