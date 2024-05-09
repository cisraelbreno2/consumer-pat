package br.com.alelo.consumer.consumerpat.exception;

public class ConsumerNotFoundException extends RuntimeException{
    public ConsumerNotFoundException(String message) {
        super(message);
    }
}
