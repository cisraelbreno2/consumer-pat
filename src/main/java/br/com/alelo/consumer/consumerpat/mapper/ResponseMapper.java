package br.com.alelo.consumer.consumerpat.mapper;

import br.com.alelo.consumer.consumerpat.dto.*;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Devido a impossibilidade de usar a biblioteca ModelMapper ou MapStruct, foi criado essa classe para mapear os objetos de responses para entity.
 * Talvez seja interessante avaliar a adição de tal biblioteca para facilitar a conversão de objetos.
 */

public class ResponseMapper {

    public static ConsumerResponse toConsumer(Consumer consumer){
        ConsumerResponse response = ConsumerResponse
                .builder()
                .id(consumer.getId())
                .name(consumer.getName())
                .documentNumber(consumer.getDocumentNumber())
                .birthDate(consumer.getBirthDate())
                .mobilePhoneNumber(consumer.getMobilePhoneNumber())
                .residencePhoneNumber(consumer.getResidencePhoneNumber())
                .phoneNumber(consumer.getPhoneNumber())
                .email(consumer.getEmail())
                .street(consumer.getStreet())
                .number(consumer.getNumber())
                .city(consumer.getCity())
                .country(consumer.getCountry())
                .postalCode(consumer.getPostalCode())
                .build();

        if(Objects.nonNull(consumer.getCards()) && !consumer.getCards().isEmpty()){
            response.setCards(
                    consumer.getCards().stream().map(ResponseMapper::toCard).collect(Collectors.toSet())
            );
        }

        return response;
    }

    public static CardResponse toCard(Card card){

        return CardResponse
                .builder()
                .cardType(card.getCardType())
                .cardNumber(card.getCardNumber())
                .balance(card.getBalance())
                .build();
    }

    public static ExtractResponse toExtract(Extract extract){
        return ExtractResponse.builder()
                .establishmentName(extract.getEstablishmentName())
                .productDescription(extract.getProductDescription())
                .dateBuy(extract.getDateBuy())
                .amount(extract.getAmount())
                .build();
    }

    public static ConsumerDetailResponse toConsumerDetail(Consumer consumer, List<Extract> extracts) {
        ConsumerDetailResponse response = ConsumerDetailResponse
                .builder()
                .name(consumer.getName())
                .documentNumber(consumer.getDocumentNumber())
                .birthDate(consumer.getBirthDate())
                .mobilePhoneNumber(consumer.getMobilePhoneNumber())
                .residencePhoneNumber(consumer.getResidencePhoneNumber())
                .phoneNumber(consumer.getPhoneNumber())
                .email(consumer.getEmail())
                .street(consumer.getStreet())
                .number(consumer.getNumber())
                .city(consumer.getCity())
                .country(consumer.getCountry())
                .postalCode(consumer.getPostalCode())
                .build();

        if(Objects.nonNull(consumer.getCards()) && !consumer.getCards().isEmpty()){
            response.setCards(
                    consumer.getCards().stream().map(c -> ResponseMapper.toCardDetail(c, extracts))
                            .collect(Collectors.toSet())
            );
        }

        return response;
    }

    private static CardDetailResponse toCardDetail(Card c, List<Extract> extracts) {
        CardDetailResponse cardDetailResponse = CardDetailResponse
                .builder()
                .cardType(c.getCardType())
                .cardNumber(c.getCardNumber())
                .balance(c.getBalance())
                .build();

        if(Objects.nonNull(extracts) && !extracts.isEmpty()){
            cardDetailResponse.setExtracts(
                    extracts.stream().map(ResponseMapper::toExtractDetail).collect(Collectors.toSet())
            );
        }
        return cardDetailResponse;
    }

    private static ExtractDetailResponse toExtractDetail(Extract extract) {
        return ExtractDetailResponse.builder()
                .establishmentName(extract.getEstablishmentName())
                .productDescription(extract.getProductDescription())
                .dateBuy(extract.getDateBuy())
                .amount(extract.getAmount())
                .build();
    }
}
