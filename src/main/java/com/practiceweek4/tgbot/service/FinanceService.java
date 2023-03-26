package com.practiceweek4.tgbot.service;

import com.practiceweek4.tgbot.entity.Income;
import com.practiceweek4.tgbot.entity.Spend;
import com.practiceweek4.tgbot.repository.IncomeRepository;
import com.practiceweek4.tgbot.repository.SpendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final SpendRepository spendRepository;
    private final IncomeRepository incomeRepository;
    private static final String ADD_INCOME = "/addincome";

    public String addFinOperation(String opertaionName, String price, Long chatID) {
        String message;
        if (ADD_INCOME.equalsIgnoreCase(opertaionName)) {
            Income income = new Income();
            income.setChat_id(chatID);
            income.setIncome(new BigDecimal(price));
            incomeRepository.save(income);
            message =  "Доход в размере " + price + " был успешно добавлен";
        } else {
            Spend spend = new Spend();
            spend.setChatId(chatID);
            spend.setSpend(new BigDecimal(price));
            spendRepository.save(spend);
            message = "Расход в размере " + price + " был успешно добавлен";
        }
    return message;
    }
}

