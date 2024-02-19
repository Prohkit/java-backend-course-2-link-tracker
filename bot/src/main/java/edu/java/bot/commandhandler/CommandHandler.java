package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommandHandler {

    protected TelegramBot telegramBot;
    protected String command;

    public abstract boolean handleCommand(UpdateWrapper update);
}
