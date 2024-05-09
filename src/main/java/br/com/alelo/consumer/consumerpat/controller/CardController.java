package br.com.alelo.consumer.consumerpat.controller;

import br.com.alelo.consumer.consumerpat.business.CardBusiness;
import br.com.alelo.consumer.consumerpat.dto.BalanceRequest;
import br.com.alelo.consumer.consumerpat.dto.BuyRequest;
import br.com.alelo.consumer.consumerpat.exception.standard.StandardRestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Criei uma classe responsavel apenas por controlar as operações do cartão.
 *  TODOS os objetos que referenciavam uma entidade diretamente foram convertidos para DTOs.
 *  Modificado a anotacao @Controller para @RestController para evitar a necessidade de anotar todos os metodos com @ResponseBody.
 *  Modificado a anotacao @Autowired para @RequiredArgsConstructor pois o proprio Spring nao recomenda o uso de @Autowired.
 *  Retirado todas as regras de negocio da controller, deixando apenas a chamada para a classe business.
 *  Extendi a classe StandardRestHandler para padronizar o retorno das respostas.
 */

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController extends StandardRestHandler {

    private final CardBusiness cardBusiness;

    /** Método responsavel por adicionar valor ao cartão */
    @PutMapping("/balance")
    public ResponseEntity<String> setBalance(@RequestBody BalanceRequest balance) {
        return new ResponseEntity<>(String.format("Novo saldo: R$ %s", cardBusiness.setBalance(balance)), HttpStatus.OK);
    }

    /** Método responsavel por efetuar a compra com o cartão */
    @PostMapping( "/buy")
    public ResponseEntity<String> buy(@RequestBody BuyRequest buy) {
        cardBusiness.buy(buy);
        return new ResponseEntity<>("Compra efetuada com sucesso", HttpStatus.OK);
    }
}
