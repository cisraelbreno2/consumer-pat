package br.com.alelo.consumer.consumerpat.fake.entity;

import br.com.alelo.consumer.consumerpat.entity.Consumer;

import java.util.Date;

public class ConsumerFake {

    public static Consumer getConsumer() {
        Consumer consumer = new Consumer();
        consumer.setId(1);
        consumer.setName("Fulano da Silva");
        consumer.setDocumentNumber("12345678901");
        consumer.setBirthDate(new Date());
        return consumer;
    }
}
