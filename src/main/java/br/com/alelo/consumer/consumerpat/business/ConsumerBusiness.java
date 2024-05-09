package br.com.alelo.consumer.consumerpat.business;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import br.com.alelo.consumer.consumerpat.constant.DocumentNumberValidator;
import br.com.alelo.consumer.consumerpat.dto.*;
import br.com.alelo.consumer.consumerpat.entity.Card;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import br.com.alelo.consumer.consumerpat.exception.AddConsumerCardExeption;
import br.com.alelo.consumer.consumerpat.exception.ConsumerNotFoundException;
import br.com.alelo.consumer.consumerpat.exception.SaveConsumerException;
import br.com.alelo.consumer.consumerpat.mapper.RequestMapper;
import br.com.alelo.consumer.consumerpat.mapper.ResponseMapper;
import br.com.alelo.consumer.consumerpat.repository.CardRepository;
import br.com.alelo.consumer.consumerpat.repository.ConsumerRepository;
import br.com.alelo.consumer.consumerpat.repository.ExtractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Classe de negócio para consumidor. Todas as regras de negócio relacionadas ao consumidor devem ser implementadas aqui.
 * Todas as validações de dados foram feitas aqui. Isso facilita os testes e a manutenção do código. Além disso,
 * caso mude a forma de entrada de dados, a regra de negócio não será afetada pois as validações estão centralizadas aqui.

 * Cada método possui seu próprio tratamento de exceção. Isso facilita a identificação do erro e a manutenção do código.
 * Em nenhum momento é chamado outra classe de negócio, pois isso conforme vai crescendo pode gerar um acoplamento entre as classes e dependencias circulares.
 */

@Service
@RequiredArgsConstructor
public class ConsumerBusiness {

    private final ConsumerRepository consumerRepository;
    private final CardRepository cardRepository;
    private final ExtractRepository extractRepository;

    public Page<ConsumerResponse> getAllConsumersList(Pageable pageable) {

        Page<Consumer> consumers = consumerRepository.findAll(pageable);

        return consumers.map(ResponseMapper::toConsumer);
    }

    @Transactional
    public void save(ConsumerRequest consumer) {
        if(DocumentNumberValidator.verify(consumer.getDocumentNumber())){
            throw new SaveConsumerException("Número do documento inválido");
        }
        if (Objects.nonNull(consumer.getId())){
            throw new SaveConsumerException("Identificador não deve ser preenchido");
        }
        consumerRepository.save(RequestMapper.toConsumer(consumer));
    }

    @Transactional
    public void update(ConsumerUpdateRequest consumerUpdate) {
        if(DocumentNumberValidator.verify(consumerUpdate.getDocumentNumber())){
            throw new SaveConsumerException("Número do documento inválido");
        }
        Set<Card> cards = consumerRepository.findById(consumerUpdate.getId())
                .map(Consumer::getCards)
                .orElseThrow(() -> new ConsumerNotFoundException("Consumidor não encontrado"));

        consumerRepository.save(RequestMapper.toConsumer(consumerUpdate, cards));
    }

    public Consumer findConsumerByDocumentNumber(String documentNumber) {
        return consumerRepository.findConsumerByDocumentNumber(documentNumber)
                                                .orElseThrow(() -> new ConsumerNotFoundException("Consumidor não encontrado"));
    }

    public ConsumerDetailResponse findConsumerDetailByDocumentNumber(String documentNumber) {
        Consumer consumer = findConsumerByDocumentNumber(documentNumber);
        List<Extract> extracts = extractRepository.findAllByCardConsumer(consumer);

        return ResponseMapper.toConsumerDetail(consumer, extracts);
    }

    @Transactional
    public void addConsumerCard(String documentNumber, CardType cardType, String cardNumber) {
        if (Objects.isNull(documentNumber) || documentNumber.isEmpty()) {
            throw new AddConsumerCardExeption("Número do documento não informado");
        }

        if(Objects.isNull(cardType)){
            throw new AddConsumerCardExeption("Tipo de cartão não informado");
        }

        if(Objects.isNull(cardNumber) || cardNumber.isEmpty()){
            throw new AddConsumerCardExeption("Número do cartão não informado");
        }

        if(cardRepository.findCardByCardNumberAndCardType(cardNumber, cardType).isPresent()){
            throw new AddConsumerCardExeption(("Cartão já existente"));
        }

        Consumer consumer = findConsumerByDocumentNumber(documentNumber);

        if (Objects.nonNull(consumer.getCards()) && consumer.getCards().stream().anyMatch(c -> c.getCardType().equals(cardType))){
            throw new AddConsumerCardExeption((String.format("Consumidor já possui um cartão do tipo %s", cardType.toValue())));
        }

        consumer.addCard(Card
                .builder()
                .cardNumber(cardNumber)
                .cardType(cardType)
                .balance(0d)
                .consumer(consumer)
                .build());

        consumerRepository.save(consumer);
    }
}
