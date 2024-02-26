package org.fdifrison.micro.cards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CardId;
    @NotNull
    String mobileNumber;
    @NotNull
    String cardNumber;
    @NotNull
    String cardType;
    @NotNull
    int totalLimit;
    @NotNull
    int amountUsed;
    @NotNull
    int availableAmount;

}
