package com.saubank.saubank.respository;

import com.saubank.saubank.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRespository extends JpaRepository<Card, String> {
    Optional<Card> findByCardNumber(String s);
}
