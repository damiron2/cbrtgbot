package com.practiceweek4.tgbot.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "SPEND")
public class Spend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CHAT_ID")
    private long chatId;

    @Column(name = "SPEND")
    private BigDecimal spend;
}
