package com.evgeny.FirstTelegramBot.service;

import com.evgeny.FirstTelegramBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messege = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messege){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    sendMessege(chatId, "Прости, но я тебя не понял :(");
                    break;
            }
        }
    }

    private void startCommandReceived(long chatId, String name){
        String answer = "Привет, " + name + ", с новым годом!";
        sendMessege(chatId, answer);
    }

    private void sendMessege(long chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        }catch (TelegramApiException e){

        }
    }
}
