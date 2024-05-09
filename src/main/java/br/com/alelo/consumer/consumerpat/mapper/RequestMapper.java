package br.com.alelo.consumer.consumerpat.mapper;

import br.com.alelo.consumer.consumerpat.dto.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.dto.ConsumerUpdateRequest;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Consumer;

import java.util.Set;

/**
 * Devido a impossibilidade de usar a biblioteca ModelMapper ou MapStruct, foi criado essa classe para mapear os objetos de requests para entity.
 * Talvez seja interessante avaliar a adição de tal biblioteca para facilitar a conversão de objetos.
 */

public class RequestMapper {

    public static Consumer toConsumer(ConsumerRequest request){
        return Consumer
                .builder()
                .id(request.getId())
                .name(request.getName())
                .documentNumber(request.getDocumentNumber())
                .birthDate(request.getBirthDate())
                .mobilePhoneNumber(request.getMobilePhoneNumber())
                .residencePhoneNumber(request.getResidencePhoneNumber())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .street(request.getStreet())
                .number(request.getNumber())
                .city(request.getCity())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .build();
    }

    public static Consumer toConsumer(ConsumerUpdateRequest request, Set<Card> cards){

        return Consumer
                .builder()
                .id(request.getId())
                .name(request.getName())
                .documentNumber(request.getDocumentNumber())
                .birthDate(request.getBirthDate())
                .mobilePhoneNumber(request.getMobilePhoneNumber())
                .residencePhoneNumber(request.getResidencePhoneNumber())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .street(request.getStreet())
                .number(request.getNumber())
                .city(request.getCity())
                .country(request.getCountry())
                .postalCode(request.getPostalCode())
                .cards(cards)
                .build();
    }
}
