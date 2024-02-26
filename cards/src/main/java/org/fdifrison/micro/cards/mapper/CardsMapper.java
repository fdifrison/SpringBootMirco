package org.fdifrison.micro.cards.mapper;

import org.fdifrison.micro.cards.dto.CardDTO;
import org.fdifrison.micro.cards.entity.Card;


public class CardsMapper {


    public static CardDTO mapToCardDTO(Card card) {
        if (card == null) {
            return null;
        }

        CardDTO cardDTO = new CardDTO();

        return setCardDTOFields(card, cardDTO);
    }

    public static CardDTO mapToCardDTO(Card card, CardDTO cardDTO) {
        if (card == null) {
            return null;
        }

        return setCardDTOFields(card, cardDTO);
    }

    private static CardDTO setCardDTOFields(Card card, CardDTO cardDTO) {
        cardDTO.setMobileNumber(card.getMobileNumber());
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setCardType(card.getCardType());
        cardDTO.setTotalLimit(card.getTotalLimit());
        cardDTO.setAmountUsed(card.getAmountUsed());
        cardDTO.setAvailableAmount(card.getAvailableAmount());

        return cardDTO;
    }

    public static Card mapToCard(CardDTO cardDTO) {
        if (cardDTO == null) {
            return null;
        }

        Card card = new Card();

        return setCardFields(cardDTO, card);
    }

    public static Card mapToCard(CardDTO cardDTO, Card card) {
        if (cardDTO == null) {
            return null;
        }

        return setCardFields(cardDTO, card);
    }

    private static Card setCardFields(CardDTO cardDTO, Card card) {
        card.setMobileNumber(cardDTO.getMobileNumber());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardType(cardDTO.getCardType());
        card.setTotalLimit(cardDTO.getTotalLimit());
        card.setAmountUsed(cardDTO.getAmountUsed());
        card.setAvailableAmount(cardDTO.getAvailableAmount());

        return card;
    }


}
