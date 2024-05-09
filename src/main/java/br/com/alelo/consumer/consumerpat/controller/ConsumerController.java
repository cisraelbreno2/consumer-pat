package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.business.ConsumerBusiness;
import br.com.alelo.consumer.consumerpat.dto.*;
import br.com.alelo.consumer.consumerpat.exception.standard.StandardRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Separei a responsabilidade da classe. Agora ela cuida apenas dos consumidores.
 *  TODOS os objetos que referenciavam uma entidade diretamente foram convertidos para DTOs.
 *  Modificado a anotacao @Controller para @RestController para evitar a necessidade de anotar todos os metodos com @ResponseBody.
 *  Modificado a anotacao @Autowired para @RequiredArgsConstructor pois o proprio Spring nao recomenda o uso de @Autowired.
 *  Retirado todas as regras de negocio da controller, deixando apenas a chamada para a classe business.
 *  Extendi a classe StandardRestHandler para padronizar o retorno das respostas.
 */

@RestController
@RequestMapping("/consumer")
@RequiredArgsConstructor
public class ConsumerController extends StandardRestHandler {

    private final ConsumerBusiness consumerBusiness;

    /** Por conter mais de 50.000 registros, ouve a necessidade de padronizar um valor para buscar apenas os 30 primeiros registros.
     * Caso necessario, basta mandar os valores page e size como parametro */

    @GetMapping()
    public ResponseEntity<Page<ConsumerResponse>> listAllConsumers(@RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "30") int size) {
        return new ResponseEntity<>(consumerBusiness.getAllConsumersList(PageRequest.of(page, size)), HttpStatus.OK);
    }

    /** Método responsavel por buscar um consumidor com todos os detalhes (Dados do Consumidor, dos cartoes e extrado de cada cartão) pelo numero do documento */
    @GetMapping("/{documentNumber}")
    public ResponseEntity<ConsumerDetailResponse> getConsumerByDocumentNumber(@PathVariable String documentNumber) {
        return new ResponseEntity<>(consumerBusiness.findConsumerDetailByDocumentNumber(documentNumber), HttpStatus.OK);
    }

    @PostMapping( "/create")
    public ResponseEntity<String> createConsumer(@RequestBody ConsumerRequest consumer) {
        consumerBusiness.save(consumer);
        return new ResponseEntity<>("Consumidor ".concat(consumer.getName()).concat(" criado com sucesso!") , HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateConsumer(@RequestBody ConsumerUpdateRequest consumerUpdate) {
        consumerBusiness.update(consumerUpdate);
        return new ResponseEntity<>("Consumidor ".concat(consumerUpdate.getName()).concat(" atualizado com sucesso!"), HttpStatus.OK);
    }

    /** Método responsavel por adicionar um cartão ao consumidor.
     * Existia a possibilidade de colocar esse metodo dentro do CardController, mas como a adição de um cartão tem haver com o consumidor
     * Eu deixei aqui*/

    @PostMapping( "/addcard")
    public ResponseEntity<String> addCard(@RequestBody CardRequest cardRequest) {
        consumerBusiness.addConsumerCard(cardRequest.getDocumentNumber(), cardRequest.getType(), cardRequest.getCardNumber());
        return new ResponseEntity<>("Cartão adicionado com sucesso!", HttpStatus.CREATED);
    }

}
