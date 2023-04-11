package com.practiceweek4.tgbot.repository;

import com.practiceweek4.tgbot.entity.Income;
import com.practiceweek4.tgbot.entity.Spend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
