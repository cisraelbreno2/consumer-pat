package br.com.alelo.consumer.consumerpat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class ConsumerResponse {

    private Integer id;
    private String name;
    private String documentNumber;
    private Date birthDate;

    private String mobilePhoneNumber;
    private String residencePhoneNumber;
    private String phoneNumber;
    private String email;

    private String street;
    private String number;
    private String city;
    private String country;
    private String postalCode;

    private Set<CardResponse> cards;

}
