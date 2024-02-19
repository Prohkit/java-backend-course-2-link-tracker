package edu.java.bot.listener;

import edu.java.bot.commandhandler.CommandHandlersChain;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TelegramBotUpdateHandler {

    private final CommandHandlersChain commandHandlersChain;

    public TelegramBotUpdateHandler(CommandHandlersChain commandHandlersChain) {
        this.commandHandlersChain = commandHandlersChain;
    }

    public void handle(UpdateWrapper update) {
        commandHandlersChain.handleCommand(update);
    }
}
