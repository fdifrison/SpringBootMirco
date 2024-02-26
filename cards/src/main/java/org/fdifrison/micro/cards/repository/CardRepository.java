package org.fdifrison.micro.cards.repository;

import org.fdifrison.micro.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByMobileNumber(String mobileNumber);
    Optional<Card> findByCardNumber(String cardNumber);

    void deleteByMobileNumber(String mobileNumber);

}
