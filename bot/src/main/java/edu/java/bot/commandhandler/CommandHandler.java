package edu.java.bot.commandhandler;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.listener.TelegramBotUpdateListener;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommandHandler {
    private CommandHandler next;
    protected final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);
    protected TelegramBot telegramBot;
    protected String command;

    public static CommandHandler link(CommandHandler first, CommandHandler... chain) {
        CommandHandler head = first;
        for (CommandHandler nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean handleCommand(UpdateWrapper update);

    protected boolean handleNext(UpdateWrapper update) {
        if (next == null) {
            return true;
        }
        return next.handleCommand(update);
    }
}
