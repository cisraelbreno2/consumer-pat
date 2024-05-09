package br.com.alelo.consumer.consumerpat.fake.dto;

import br.com.alelo.consumer.consumerpat.dto.ConsumerRequest;

import java.util.Date;

public class ConsumerRequestFake {

    public static ConsumerRequest getConsumerRequest() {
        return ConsumerRequest.builder()
                .name("Fulano da Silva")
                .documentNumber("12345678901")
                .birthDate(new Date())
                .email("teste@testando.com")
                .residencePhoneNumber("12")
                .mobilePhoneNumber("123456789")
                .phoneNumber("123456789")
                .street("Rua teste")
                .number("123")
                .city("Cidade teste")
                .country("País teste")
                .postalCode("12345678")
                .build();
    }
}
