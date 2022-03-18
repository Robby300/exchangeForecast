package com.exchangeForecast.service.outputServcie;

import com.exchangeForecast.bot.Bot;
import com.exchangeForecast.command.CommandUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class SendMessageServiceImpl implements SendMessageService {
    private static final Logger logger = LoggerFactory.getLogger(SendMessageServiceImpl.class);
    private final Bot bot;
    private final Update update;

    public SendMessageServiceImpl(Bot bot, Update update) {
        this.bot = bot;
        this.update = update;
    }

    @Override
    public void sendMessage(String message) {
        Long chatId = CommandUtils.getChatId(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Ошибка отправки сообщения.");
            e.printStackTrace();
        }
    }

    @Override
    public void sendPhoto() {
        Long chatId = CommandUtils.getChatId(update);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(new File("/tmp/histogram.png")));
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}