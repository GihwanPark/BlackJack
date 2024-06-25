package com.example.blackjack21.repository;

import com.example.blackjack21.model.Card;
import com.example.blackjack21.service.DealerService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class CardRepository {
    private Map<String, Card> cardMap;
    DealerService dealerService;

    @PostConstruct
    private void init(){
        cardMap = new LinkedHashMap<>();
    }

}
