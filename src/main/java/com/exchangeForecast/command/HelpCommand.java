package com.exchangeForecast.command;

import com.exchangeForecast.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exchangeForecast.command.CommandName.*;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"
                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "%s - получить прогноз курсов валют:\n"
                    + "rate cdx [period]\n"
                    + "    cdx:\n"
                    + "       USD     USD [period]\n"
                    + "       TRY     TRY [period]\n"
                    + "       EUR     EUR [period]\n"
                    + "    period:\n"
                    + "       tomorrow    [tomorrow]\n"
                    + "       week        [week]",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), RATE.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}