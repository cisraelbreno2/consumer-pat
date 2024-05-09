package br.com.alelo.consumer.consumerpat.fake.entity;

import br.com.alelo.consumer.consumerpat.entity.Extract;

import java.util.Date;

public class ExtractFake {

    public static Extract getExtract() {
        Extract extract = new Extract();
        extract.setId(1);
        extract.setEstablishmentName("Estabelecimento");
        extract.setProductDescription("Descrição do produto");
        extract.setDateBuy(new Date());
        extract.setAmount(100.0);
        return extract;
    }
}
