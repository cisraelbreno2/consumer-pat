package br.com.alelo.consumer.consumerpat.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * Este enum representa os tipos de estabelecimentos que podem ser utilizados para realizar compras e aplica regras para cada tipo de estabelecimento.
 */

@Getter
public enum EstablishmentType {
    DRUGSTORE(CardType.DRUGSTORE_CARD){
        public Double applyRule(Double value) {return value;}
    },
    FOOD(CardType.FOOD_CARD){
        public Double applyRule(Double value) {
            Double cashback  = (value / 100) * 10;
            return value - cashback;
        }
    },
    FUEL(CardType.FUEL_CARD){
        public Double applyRule(Double value) {
            Double tax  = (value / 100) * 35;
            return value + tax;
        }
    };

    private final CardType cardAllowed;

    public abstract Double applyRule(Double value);

    EstablishmentType(CardType cardTypeAllowed){
        this.cardAllowed = cardTypeAllowed;
    }

    @JsonValue
    public String toValue() {
        return toString();
    }
}
