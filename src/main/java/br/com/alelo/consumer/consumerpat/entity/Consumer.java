package br.com.alelo.consumer.consumerpat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Consumer: Foi adicionado a anotação @Builder para facilitar a criação de objetos, foi adicionado a anotação @NoArgsConstructor para facilitar a criação de objetos
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class Consumer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(nullable = false)
    String name;
    /** documentNumber: Foi adicionado a anotação @Column(unique = true) para garantir que não exista dois consumidores
     *  com o mesmo documento e foi modificado de Int para String evitando assim problemas com 0 a esquerda */
    @Column(unique = true)
    String documentNumber;

    @Column(nullable = false)
    Date birthDate;

    /**
     * mobilePhoneNumber: Foi alterado o tipo de dado de Integer para String para evitar problemas com 0 a esquerda
     * residencePhoneNumber: Foi alterado o tipo de dado de Integer para String para evitar problemas com 0 a esquerda
     * phoneNumber: Foi alterado o tipo de dado de Integer para String para evitar problemas com 0 a esquerda
     */
    @Column
    String mobilePhoneNumber;
    @Column
    String residencePhoneNumber;
    @Column
    String phoneNumber;
    @Column(nullable = false)
    String email;

    /**
     * number: Foi alterado o tipo de dado de Integer para String para pode adicionar a letra s/n caso necessário
     * postalCode: Foi alterado o tipo de dado de Integer para String para evitar problemas com 0 a esquerda.
     */
    @Column(nullable = false)
    String street;
    @Column(nullable = false)
    String number;
    @Column(nullable = false)
    String city;
    @Column(nullable = false)
    String country;
    @Column(nullable = false)
    String postalCode;

    /**
     * cards: Foi alterado a logica de mapeamento para OneToMany, pois no futuro pode existir mais do que três cartões e para facilitar a manutenção do código
     *        foi alterado para OneToMany e adicionado o método addCard para adicionar um cartão ao consumidor. Caso precise adicionar um novo tipo de cartão
     *        basta adicionar dentro de @CardType e estabelecer novas regras dentro de @EstablishmentType.
     *        Coloquei no OneToMany o FetchType.EAGER para que sempre que buscar um consumidor ele traga os cartões, já que é uma classe relativamente leve.
     *        Mas caso seja necessário melhorar a performance pode ser alterado para LAZY.
     */
    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Card> cards;

    public void addCard(Card card){
        if(Objects.isNull(this.cards)){
            this.cards = new HashSet<>();
        }
        this.cards.add(card);
    }

}
