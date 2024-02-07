package edu.java.bot.listener;

import edu.java.bot.commandhandler.CommandHandlersChain;
import edu.java.bot.updatewrapper.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotUpdateHandler {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdateListener.class);

    private final CommandHandlersChain commandHandlersChain;

    public TelegramBotUpdateHandler(CommandHandlersChain commandHandlersChain) {
        this.commandHandlersChain = commandHandlersChain;
    }

    public void handle(UpdateWrapper update) {
        commandHandlersChain.getCommandHandler().handleCommand(update);
    }
}
