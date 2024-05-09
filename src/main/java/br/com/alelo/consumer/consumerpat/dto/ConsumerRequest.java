package br.com.alelo.consumer.consumerpat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ConsumerRequest {

    private Integer id;
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String documentNumber;
    private Date birthDate;

    private String mobilePhoneNumber;
    private String residencePhoneNumber;
    private String phoneNumber;
    @JsonProperty(required = true)
    @Email
    private String email;

    private String street;
    private String number;
    private String city;
    private String country;
    private String postalCode;
}
