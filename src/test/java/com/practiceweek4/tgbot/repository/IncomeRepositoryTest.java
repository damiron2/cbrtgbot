package com.practiceweek4.tgbot.repository;

import com.practiceweek4.tgbot.entity.Income;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class IncomeRepositoryTest {

    @Autowired
    private IncomeRepository incomeRepository;

    @Test
    public void testDataScripts() {
        Optional<Income> incomeById = incomeRepository.findById(12L);
        assertTrue(incomeById.isPresent());
        Assertions.assertEquals(new BigDecimal("3000.00"),incomeById.get().getIncome());
    }
}