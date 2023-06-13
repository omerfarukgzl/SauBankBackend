package com.saubank.saubank.service;

import com.saubank.saubank.model.Card;
import com.saubank.saubank.respository.CardRespository;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRespository cardRespository;

    public CardService(CardRespository cardRespository) {
        this.cardRespository = cardRespository;
    }

    public Card getCard(String cardNumber){
        return cardRespository.findByCardNumber(cardNumber).orElseThrow(() -> new RuntimeException("Kart bulunamadı."));
    }

}
