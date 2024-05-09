package br.com.alelo.consumer.consumerpat.dto;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardRequest {

    @JsonProperty(required = true)
    private String documentNumber;
    @JsonProperty(required = true)
    private CardType type;
    @JsonProperty(required = true)
    private String cardNumber;
}
