package br.com.alelo.consumer.consumerpat.business.impl;

import br.com.alelo.consumer.consumerpat.business.CardBusiness;
import br.com.alelo.consumer.consumerpat.business.impl.establishmentStrategy.EstablishmentStrategy;
import br.com.alelo.consumer.consumerpat.dto.BalanceRequest;
import br.com.alelo.consumer.consumerpat.dto.BuyRequest;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.exception.BuyException;
import br.com.alelo.consumer.consumerpat.exception.SetBalanceCardExeption;
import br.com.alelo.consumer.consumerpat.repository.CardRepository;
import br.com.alelo.consumer.consumerpat.repository.ExtractRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static br.com.alelo.consumer.consumerpat.constant.BalanceCardStandard.ESTABLISHMENT_NAME;
import static br.com.alelo.consumer.consumerpat.constant.BalanceCardStandard.PRODUCT_DESCRIPTION;

/**
 * Classe de negócio para as transaçoes de cartão. Todas as regras de negócio relacionadas ao consumidor devem ser implementadas aqui.
 * Todas as validações de dados foram feitas aqui. Isso facilita os testes e a manutenção do código. Além disso,
 * caso mude a forma de entrada de dados, a regra de negócio não será afetada pois as validações estão centralizadas aqui.

 * Cada método possui seu próprio tratamento de exceção. Isso facilita a identificação do erro e a manutenção do código.
 * Em nenhum momento é chamado outra classe de negócio, pois isso conforme vai crescendo pode gerar um acoplamento entre as classes e dependencias circulares.
 */

@Service
public class CardBusinessImpl implements CardBusiness {

    private final CardRepository cardRepository;
    private final ExtractRepository extractRepository;

    private final List<EstablishmentStrategy> establishmentStrategys;

    public CardBusinessImpl(CardRepository cardRepository, ExtractRepository extractRepository, List<EstablishmentStrategy> establishmentStrategys) {
        this.cardRepository = cardRepository;
        this.extractRepository = extractRepository;
        this.establishmentStrategys = establishmentStrategys;
    }

    @Transactional
    public BigDecimal setBalance(BalanceRequest balance) {
        if (Objects.isNull(balance.getValue()) || balance.getValue().intValue() < 0) {
            throw new SetBalanceCardExeption("Saldo não informado");
        }
        if(Objects.isNull(balance.getCardNumber()) || balance.getCardNumber().isEmpty()){
            throw new SetBalanceCardExeption("Número do cartão não informado");
        }
        if(Objects.isNull(balance.getCardType())){
            throw new SetBalanceCardExeption("Tipo do cartão não informado");
        }

        Card card = cardRepository.findCardByCardNumberAndCardType(balance.getCardNumber(), balance.getCardType())
                .orElseThrow(() -> new SetBalanceCardExeption("Cartão não encontrado"));


        card.setBalance(card.getBalance().add(balance.getValue()));

        cardRepository.save(card);
        extractRepository.save(Extract
                .builder()
                .establishmentName(ESTABLISHMENT_NAME.getDescription())
                .productDescription(PRODUCT_DESCRIPTION.getDescription())
                .card(card)
                .dateBuy(new Date())
                .amount(balance.getValue())
                .build());

        return card.getBalance();
    }

    @Transactional
    public void buy(BuyRequest buy) {
        if (Objects.isNull(buy.getEstablishmentType())) {
            throw new BuyException("Tipo de estabelecimenmto não informado");
        }
        if (Objects.isNull(buy.getCardNumber()) || buy.getCardNumber().isEmpty()) {
            throw new BuyException("Número do cartão não informado");
        }
        if(Objects.isNull(buy.getEstablishmentName()) || buy.getEstablishmentName().isEmpty()){
            throw new BuyException("Nome do estabelecimento não informado");
        }

        if (Objects.isNull(buy.getValue())){
            buy.setValue(new BigDecimal("0"));
        }

        Card card = cardRepository.findCardByCardNumberAndCardType(buy.getCardNumber(), buy.getEstablishmentType().getCardAllowed())
                .orElseThrow(() -> new BuyException("Cartão não encontrado"));

        buy.setValue(establishmentStrategys.stream().filter(establishmentStrategy -> establishmentStrategy.verifyCardType(buy.getEstablishmentType().getCardAllowed()))
                .findFirst().orElseThrow(() -> new BuyException("Tipo de estabelecimento não encontrado"))
                .applyRule(buy.getValue()));

        if (buy.getValue().compareTo(card.getBalance()) > 0)
            throw new BuyException("Saldo insuficiente");

        card.setBalance(card.getBalance().subtract(buy.getValue()));

        extractRepository.save(Extract
                .builder()
                .establishmentName(buy.getEstablishmentName())
                .productDescription(buy.getProductDescription())
                .card(card)
                .dateBuy(new Date())
                .amount(buy.getValue().multiply(new BigDecimal("-1.0")))
                .build());

        cardRepository.save(card);
    }
}
