package br.com.alelo.consumer.consumerpat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
public class ConsumerUpdateRequest {

    @JsonProperty(required = true)
    private Integer id;
    private String name;
    private String documentNumber;
    private Date birthDate;
    private String mobilePhoneNumber;
    private String residencePhoneNumber;
    private String phoneNumber;
    @Email
    private String email;
    private String street;
    private String number;
    private String city;
    private String country;
    private String postalCode;

}
