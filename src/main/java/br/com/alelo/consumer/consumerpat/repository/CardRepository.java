package br.com.alelo.consumer.consumerpat.repository;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import br.com.alelo.consumer.consumerpat.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Optional<Card> findCardByCardNumberAndCardType(String CardNumber, CardType cardType);
}
