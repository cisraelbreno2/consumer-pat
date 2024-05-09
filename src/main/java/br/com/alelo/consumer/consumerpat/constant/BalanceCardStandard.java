package br.com.alelo.consumer.consumerpat.constant;

import lombok.Getter;

@Getter
public enum BalanceCardStandard {

    ESTABLISHMENT_NAME("Recarga"),
    PRODUCT_DESCRIPTION("Recarga do cartão"),;

    public final String description;

    BalanceCardStandard(String description) {
        this.description = description;
    }

}
