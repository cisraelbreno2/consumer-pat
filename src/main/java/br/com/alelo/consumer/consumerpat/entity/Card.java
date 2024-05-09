package br.com.alelo.consumer.consumerpat.entity;

import br.com.alelo.consumer.consumerpat.constant.CardType;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"consumer"})
@EqualsAndHashCode(exclude = {"consumer"})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    /**
     * cardType: Foi adicionado a anotação @Enumerated(EnumType.STRING) para garantir que o valor salvo no banco seja um Enum
     *           evitando assim problemas futuros.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    /**
     * consumer: É através do consumer que temos o link entre os cartões e os consumidores.
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "consumerId")
    private Consumer consumer;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private Double balance;

}
