package br.com.alelo.consumer.consumerpat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExtractResponse {

    private String establishmentName;
    private String productDescription;
    private Date dateBuy;
    private Double amount;


}
