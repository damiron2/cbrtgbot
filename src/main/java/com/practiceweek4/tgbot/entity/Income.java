package com.practiceweek4.tgbot.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "INCOMES")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CHAT_ID")
    private long chat_id;

    @Column(name = "INCOME")
    private BigDecimal income;
}
