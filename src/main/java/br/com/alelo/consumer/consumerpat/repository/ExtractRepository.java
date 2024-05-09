package br.com.alelo.consumer.consumerpat.repository;

import br.com.alelo.consumer.consumerpat.entity.Consumer;
import br.com.alelo.consumer.consumerpat.entity.Extract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtractRepository extends JpaRepository<Extract, Integer> {

    List<Extract> findAllByCardConsumer(Consumer card_consumer);
}
