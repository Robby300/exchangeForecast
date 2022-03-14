package com.exchangeForecast.command;

import com.exchangeForecast.service.outputServcie.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.exchangeForecast.command.CommandName.*;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"
                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "%s - получить прогноз курсов валют:\n\n"
                    + "rate [cdx,cdx,...] -period/-date [period]/[date] -alg [algorithm] -output [output]\n"
                    + "    cdx:\n"
                    + "       USD\n"
                    + "       TRY\n"
                    + "       EUR\n"
                    + "       EUR\n"
                    + "       EUR\n"
                    + "    period:\n"
                    + "       week\n"
                    + "       month\n"
                    + "    date:\n"
                    + "       tomorrow\n"
                    + "       dd.MM.yyyy\n"
                    + "    alg:\n"
                    + "       moon\n"
                    + "       linear\n"
                    + "       actual\n"
                    + "   output:\n"
                    + "       list\n"
                    + "       graph",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), RATE.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}