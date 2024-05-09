package br.com.alelo.consumer.consumerpat.dto;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CardDetailResponse {

    private CardType cardType;
    private String cardNumber;
    private Double balance;
    private Set<ExtractDetailResponse> extracts;
}
