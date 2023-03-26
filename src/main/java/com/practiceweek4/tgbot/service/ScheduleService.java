package com.practiceweek4.tgbot.service;


import com.practiceweek4.tgbot.dto.ValuteCursOnDate;
import com.practiceweek4.tgbot.entity.ActiveChat;
import com.practiceweek4.tgbot.repository.ActiveChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleService {

    private final ActiveChatRepository activeChatRepository;
    private final CentralRussianBankService centralRussianBankService;
    private final BotService botService;
    private final List<ValuteCursOnDate> previousRates;

    @Scheduled(cron = "0 0 0/3 ? * *")
    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = centralRussianBankService.getCurrenciesFromCbr();
            Set<Long> activeChatIds = activeChatRepository.findAll().stream().map(ActiveChat::getChatId).collect(Collectors.toSet());

            if (!previousRates.isEmpty()) {
                for (int index = 0; index < currentRates.size(); index++) {
                    if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10) {
                    botService.sendNotificationToAllActiveChats("Курс "+ currentRates.get(index).getCode()+" вырос на 10 рублей", activeChatIds);
                    }
                    else if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10) {
                        botService.sendNotificationToAllActiveChats("Курс "+ currentRates.get(index).getCode()+" уменьшился на 10 рублей", activeChatIds);
                    }

                }
            }
            else{
                previousRates.addAll(currentRates);
            }

        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
