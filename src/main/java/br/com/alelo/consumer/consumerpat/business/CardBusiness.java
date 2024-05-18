package br.com.alelo.consumer.consumerpat.business;

import br.com.alelo.consumer.consumerpat.dto.BalanceRequest;
import br.com.alelo.consumer.consumerpat.dto.BuyRequest;

import java.math.BigDecimal;

public interface CardBusiness {

    BigDecimal setBalance(BalanceRequest balance);

    void buy(BuyRequest buy);
}
