package br.com.alelo.consumer.consumerpat.business;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import br.com.alelo.consumer.consumerpat.dto.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.mapper.ResponseMapper;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.exception.AddConsumerCardExeption;
import br.com.alelo.consumer.consumerpat.exception.ConsumerNotFoundException;
import br.com.alelo.consumer.consumerpat.exception.SaveConsumerException;
import br.com.alelo.consumer.consumerpat.fake.dto.ConsumerRequestFake;
import br.com.alelo.consumer.consumerpat.fake.entity.CardFake;
import br.com.alelo.consumer.consumerpat.fake.entity.ConsumerFake;
import br.com.alelo.consumer.consumerpat.fake.entity.ExtractFake;
import br.com.alelo.consumer.consumerpat.repository.CardRepository;
import br.com.alelo.consumer.consumerpat.repository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.repository.ExtractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ConsumerBusinessTest {

    @InjectMocks
    private ConsumerBusiness consumerBusiness;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ExtractRepository extractRepository;

    @Mock
    private ConsumerRepository consumerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste de adição de cartão de consumidor")
    public void addConsumerCardTest() {

        String documentNumber = "12345678901";
        CardType cardType = CardType.FOOD_CARD;
        String cardNumber = "1111222233334444";

        Consumer consumer = ConsumerFake.getConsumer();

        when(consumerRepository.findConsumerByDocumentNumber(anyString())).thenReturn(Optional.of(consumer));
        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.empty());

        consumerBusiness.addConsumerCard(documentNumber, cardType, cardNumber);

        verify(consumerRepository, times(1)).save(any(Consumer.class));
    }

    @Test
    @DisplayName("Teste de adição de cartão de consumidor - Exceções")
    public void addConsumerCardTest_ThrowsException() {

        when(consumerRepository.findConsumerByDocumentNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(AddConsumerCardExeption.class, () -> consumerBusiness.addConsumerCard("", CardType.FOOD_CARD, "1111222233334444"), "Número do documento não informado");
        assertThrows(AddConsumerCardExeption.class, () -> consumerBusiness.addConsumerCard("12345678901", null, "1111222233334444"), "Tipo de cartão não informado");
        assertThrows(AddConsumerCardExeption.class, () -> consumerBusiness.addConsumerCard("12345678901", CardType.FOOD_CARD, null), "Número do cartão não informado");

        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.of(CardFake.getCard()));
        assertThrows(AddConsumerCardExeption.class, () -> consumerBusiness.addConsumerCard("123456", CardType.FOOD_CARD, "1111222233334444"), "Cartão já cadastrado");

        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.empty());
    }

    @Test
    @DisplayName("Teste de busca de consumidor por número de documento")
    public void findConsumerDetailByDocumentNumberTest() {
        String documentNumber = "11754128050";
        Consumer consumer = ConsumerFake.getConsumer();
        consumer.addCard(CardFake.getCard());
        List<Extract> extracts = Collections.singletonList(ExtractFake.getExtract());

        when(consumerRepository.findConsumerByDocumentNumber(anyString())).thenReturn(Optional.of(consumer));
        when(extractRepository.findAllByCardConsumer(consumer)).thenReturn(extracts);

        var result = consumerBusiness.findConsumerDetailByDocumentNumber(documentNumber);

        assertEquals(result, ResponseMapper.toConsumerDetail(consumer, extracts));
    }

    @Test
    @DisplayName("Teste de busca de consumidor por número de documento - Exceção")
    public void findConsumerDetailByDocumentNumberTest_ThrowsException() {

        String documentNumber = "12345678901";
        when(consumerRepository.findConsumerByDocumentNumber(anyString())).thenReturn(Optional.empty());
        assertThrows(ConsumerNotFoundException.class, () -> consumerBusiness.findConsumerDetailByDocumentNumber(documentNumber), "Consumidor não encontrado");
    }

    @Test
    @DisplayName("Teste de inserção de consumidor - Exceção")
    public void saveShouldThrowExceptionWhenConsumerIdIsNotNull() {
        ConsumerRequest consumerRequest = ConsumerRequestFake.getConsumerRequest();
        consumerRequest.setId(1);

        assertThrows(SaveConsumerException.class, () -> consumerBusiness.save(consumerRequest), "Identificador não deve ser preenchido");
    }

    @Test
    @DisplayName("Teste de inserção de consumidor")
    public void saveShouldCallRepositorySaveWhenConsumerIdIsNull() {
        ConsumerRequest consumerRequest = ConsumerRequestFake.getConsumerRequest();
        consumerRequest.setId(null);

        consumerBusiness.save(consumerRequest);

        verify(consumerRepository, times(1)).save(any(Consumer.class));
    }



}
