package br.com.alelo.consumer.consumerpat.business;

import br.com.alelo.consumer.consumerpat.dto.BalanceRequest;
import br.com.alelo.consumer.consumerpat.dto.BuyRequest;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.exception.BuyException;
import br.com.alelo.consumer.consumerpat.exception.SetBalanceCardExeption;
import br.com.alelo.consumer.consumerpat.fake.dto.BalanceRequestFake;
import br.com.alelo.consumer.consumerpat.fake.dto.BuyRequestFake;
import br.com.alelo.consumer.consumerpat.fake.entity.CardFake;
import br.com.alelo.consumer.consumerpat.fake.entity.ExtractFake;
import br.com.alelo.consumer.consumerpat.repository.CardRepository;
import br.com.alelo.consumer.consumerpat.repository.ExtractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class CardBusinessTest {

    @InjectMocks
    private CardBusiness cardBusiness;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ExtractRepository extractRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste de inserção de saldo")
    public void setBalanceTest() {

        BalanceRequest balanceRequest = BalanceRequestFake.getBalanceRequest();
        Card card = CardFake.getCard();

        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.of(card));
        cardBusiness.setBalance(balanceRequest);

        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Teste de exceção ao inserir saldo")
    public void setBalanceTest_ThrowsException() {
        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.empty());

        BalanceRequest balanceRequestWithoutValue = BalanceRequestFake.getBalanceRequestWithoutValue();
        assertThrows(SetBalanceCardExeption.class, () -> cardBusiness.setBalance(balanceRequestWithoutValue), "Saldo não informado");

        BalanceRequest balanceRequestWithNegativeValue = BalanceRequestFake.getBalanceRequestWithoutCardNumber();
        assertThrows(SetBalanceCardExeption.class, () -> cardBusiness.setBalance(balanceRequestWithNegativeValue), "Número do cartão não informado");

        BalanceRequest balanceRequestWithoutCardType = BalanceRequestFake.getBalanceRequestWithoutCardType();
        assertThrows(SetBalanceCardExeption.class, () -> cardBusiness.setBalance(balanceRequestWithoutCardType), "Tipo do cartão não informado");
    }

    @Test
    @DisplayName("Teste de compra")
    public void buyTest() {

        BuyRequest buyRequest = BuyRequestFake.getBuyRequest();
        Card card = CardFake.getCard();
        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.of(card));
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(extractRepository.save(any())).thenReturn(ExtractFake.getExtract());
        cardBusiness.buy(buyRequest);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Teste de exceção ao comprar")
    public void buyTest_ThrowsException() {

        when(cardRepository.findCardByCardNumberAndCardType(anyString(), any())).thenReturn(Optional.empty());

        BuyRequest buyRequestWithoutCardNumber = BuyRequestFake.getBuyRequestWithoutCardNumber();
        assertThrows(BuyException.class, () -> cardBusiness.buy(buyRequestWithoutCardNumber), "Número do cartão não informado");

        BuyRequest buyRequestWithoutValue = BuyRequestFake.getBuyRequestWithoutEstablishmentType();
        assertThrows(BuyException.class, () -> cardBusiness.buy(buyRequestWithoutValue), "Tipo de estabelecimenmto não informado");

        BuyRequest buyRequestWithoutEstablishmentName = BuyRequestFake.getBuyRequestWithoutEstablishmentName();
        assertThrows(BuyException.class, () -> cardBusiness.buy(buyRequestWithoutEstablishmentName), "Nome do estabelecimento não informado");

    }




}