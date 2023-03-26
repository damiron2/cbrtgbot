package com.practiceweek4.tgbot.service;

import com.practiceweek4.tgbot.dto.ValuteCursOnDate;
import com.practiceweek4.tgbot.entity.ActiveChat;
import com.practiceweek4.tgbot.repository.ActiveChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final CentralRussianBankService centralRussianBankService;
    private final ActiveChatRepository activeChatRepository;
    private final FinanceService financeService;

    private Map<Long, List<String>> prevMessage = new ConcurrentHashMap<>();

    private static final String GET_COURSES = "/currentrates";
    private static final String ADD_INCOME = "/addincome";
    private static final String ADD_SPEND = "/addspend";

    @Value("${bot.api.key}")
    private String botApiKey;

    @Value("${bot.name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botApiKey;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        log.debug(message.getText());
        try {
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));
            if (GET_COURSES.equalsIgnoreCase(message.getText())) {
                for (ValuteCursOnDate valuteCursOnDate : centralRussianBankService.getCurrenciesFromCbr()) {
                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") + valuteCursOnDate.getName()
                            + " - " + valuteCursOnDate.getNominal() + " " + valuteCursOnDate.getChCode() + " = " + valuteCursOnDate.getCourse() + " RUB" + "\n");
                }
            } else if (ADD_INCOME.equalsIgnoreCase(message.getText())) {
                response.setText("Напишите сумму доходов");
            } else if (ADD_SPEND.equalsIgnoreCase(message.getText())) {
                response.setText("Напишите сумму расходов");
            } else {
                response.setText(financeService.addFinOperation(getPrevCommand(message.getChatId()), message.getText(), message.getChatId()));
            }
            putPrevCommand(message.getChatId(), message.getText());
            execute(response);

            if (activeChatRepository.findActiveChatByChatId(chatId).isEmpty()) {
                ActiveChat activeChat = new ActiveChat();
                activeChat.setChatId(chatId);
                activeChatRepository.save(activeChat);
            }


        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void sendNotificationToAllActiveChats(String message, Set<Long> chatIds) {

        for (Long id : chatIds) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(id));
            sendMessage.setText(message);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private void putPrevCommand(Long chatId, String command) {
        if (prevMessage.get(chatId) == null) {
            List<String> commands = new ArrayList<>();
            commands.add(command);
            prevMessage.put(chatId, commands);
        }
        prevMessage.get(chatId).add(command);
    }

    private String getPrevCommand(Long chatId) {
        return prevMessage.get(chatId).get(prevMessage.get(chatId).size() - 1);
    }
}
