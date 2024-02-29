package edu.java.bot.commandhandler;

import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommandHandlersChain {

    private final List<CommandHandler> handlerList;

    private final SendMessageService messageService;

    public CommandHandlersChain(List<CommandHandler> handlerList, SendMessageService messageService) {
        this.handlerList = handlerList;
        this.messageService = messageService;
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

    private void handleUnknownCommand(UpdateWrapper update) {
        messageService.sendMessage(update, "Неизвестная команда");
        log.info("Ничего не делаем");
    }
}
