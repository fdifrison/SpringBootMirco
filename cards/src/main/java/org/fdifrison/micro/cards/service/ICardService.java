package org.fdifrison.micro.cards.service;

import org.fdifrison.micro.cards.dto.CardDTO;

public interface ICardService {

    void creatCard(String mobileNUmber);

    CardDTO fetchCard(String mobileNumber);

    boolean updateCard(CardDTO cardDTO);

    boolean deleteCard(String mobileNumber);

}
