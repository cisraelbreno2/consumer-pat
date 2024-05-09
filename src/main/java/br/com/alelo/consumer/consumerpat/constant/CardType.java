package br.com.alelo.consumer.consumerpat.constant;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CardType {
    FOOD_CARD,
    DRUGSTORE_CARD,
    FUEL_CARD;

    @JsonValue
    public String toValue() {
        return toString();
    }
}