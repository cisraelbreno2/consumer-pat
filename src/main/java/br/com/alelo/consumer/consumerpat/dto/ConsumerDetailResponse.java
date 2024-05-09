package br.com.alelo.consumer.consumerpat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ConsumerDetailResponse {

    String name;
    String documentNumber;
    Date birthDate;
    String mobilePhoneNumber;
    String residencePhoneNumber;
    String phoneNumber;
    String email;
    String street;
    String number;
    String city;
    String country;
    String postalCode;
    Set<CardDetailResponse> cards;
}
