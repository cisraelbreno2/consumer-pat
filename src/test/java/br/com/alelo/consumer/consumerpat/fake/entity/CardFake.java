package br.com.alelo.consumer.consumerpat.fake.entity;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import br.com.alelo.consumer.consumerpat.entity.Card;

public class CardFake {

    public static Card getCard() {
        Card card = new Card();
        card.setId(1);
        card.setCardNumber("1234567890123456");
        card.setCardType(CardType.FOOD_CARD);
        card.setBalance(100.0);
        card.setConsumer(ConsumerFake.getConsumer());
        return card;
    }

}
