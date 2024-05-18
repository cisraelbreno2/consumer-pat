package br.com.alelo.consumer.consumerpat.business;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import br.com.alelo.consumer.consumerpat.dto.ConsumerDetailResponse;
import br.com.alelo.consumer.consumerpat.dto.ConsumerRequest;
import br.com.alelo.consumer.consumerpat.dto.ConsumerResponse;
import br.com.alelo.consumer.consumerpat.dto.ConsumerUpdateRequest;
import br.com.alelo.consumer.consumerpat.entity.Consumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConsumerBusiness {

    Page<ConsumerResponse> getAllConsumersList(Pageable pageable);

    void save(ConsumerRequest consumer);

    void update(ConsumerUpdateRequest consumerUpdate);

    Consumer findConsumerByDocumentNumber(String documentNumber);

    ConsumerDetailResponse findConsumerDetailByDocumentNumber(String documentNumber);

    void addConsumerCard(String documentNumber, CardType cardType, String cardNumber);
}
