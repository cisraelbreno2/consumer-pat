package br.com.alelo.consumer.consumerpat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Builder: com o builder evitamos muitos boilerplate dentro da classe e tambem nos dá flexibilidade na montagem do objeto.
 * @Column(nullable = false): Foi adicionado para garantir que os campos não sejam nulos.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String establishmentName;

    @Column(nullable = false)
    private String productDescription;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBuy;

    /**
     * card: É através do card que temos o link entre os extratos e os cartões.
     */
    @ManyToOne
    @JoinColumn(name = "cardId", referencedColumnName = "id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private Double amount;

}
