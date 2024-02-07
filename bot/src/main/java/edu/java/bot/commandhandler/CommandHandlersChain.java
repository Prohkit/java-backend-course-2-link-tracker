package edu.java.bot.commandhandler;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CommandHandlersChain {

    private CommandHandler commandHandler;

    private final StartCommandHandler startCommandHandler;

    private final ListCommandHandler listCommandHandler;

    private final TrackCommandHandler trackCommandHandler;

    private final UntrackCommandHandler untrackCommandHandler;

    private final HelpCommandHandler helpCommandHandler;

    private final UnknownCommandHandler unknownCommandHandler;

    public CommandHandlersChain(
        StartCommandHandler startCommandHandler,
        ListCommandHandler listCommandHandler,
        TrackCommandHandler trackCommandHandler,
        UntrackCommandHandler untrackCommandHandler,
        HelpCommandHandler helpCommandHandler,
        UnknownCommandHandler unknownCommandHandler
    ) {
        this.startCommandHandler = startCommandHandler;
        this.listCommandHandler = listCommandHandler;
        this.trackCommandHandler = trackCommandHandler;
        this.untrackCommandHandler = untrackCommandHandler;
        this.helpCommandHandler = helpCommandHandler;
        this.unknownCommandHandler = unknownCommandHandler;
    }

    @PostConstruct
    void init() {
        initChainCommandHanlders();
    }

    private void initChainCommandHanlders() {
        commandHandler = CommandHandler.link(
            startCommandHandler,
            trackCommandHandler,
            untrackCommandHandler,
            helpCommandHandler,
            listCommandHandler,
            unknownCommandHandler
        );
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
