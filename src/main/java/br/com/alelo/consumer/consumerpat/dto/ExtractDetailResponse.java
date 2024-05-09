package br.com.alelo.consumer.consumerpat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ExtractDetailResponse {

    private String establishmentName;
    private String productDescription;
    private Date dateBuy;
    private Double amount;
}
