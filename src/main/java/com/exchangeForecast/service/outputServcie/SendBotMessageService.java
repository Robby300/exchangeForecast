package com.exchangeForecast.service.outputServcie;

/**
 * Service for sending messages via telegram-bot.
 */
public interface SendBotMessageService {

    /**
     * Send message via telegram bot.
     *
     * @param message provided message to be sent.
     */
    void sendMessage(String message);
    void sendPhoto();
}
