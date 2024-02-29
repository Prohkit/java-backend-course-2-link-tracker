package edu.java.bot.commandhandler;

import edu.java.bot.service.SendMessageService;
import edu.java.bot.updatewrapper.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommandHandler {

    protected SendMessageService messageService;

    public abstract boolean handleCommand(UpdateWrapper update);
}
