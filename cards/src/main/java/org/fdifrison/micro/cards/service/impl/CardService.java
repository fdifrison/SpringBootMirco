package org.fdifrison.micro.cards.service.impl;

import org.fdifrison.micro.cards.constants.CardConstants;
import org.fdifrison.micro.cards.dto.CardDTO;
import org.fdifrison.micro.cards.entity.Card;
import org.fdifrison.micro.cards.exception.CardAlreadyExistException;
import org.fdifrison.micro.cards.exception.ResourceNotFoundException;
import org.fdifrison.micro.cards.mapper.CardsMapper;
import org.fdifrison.micro.cards.repository.CardRepository;
import org.fdifrison.micro.cards.service.ICardService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardService implements ICardService {

    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void creatCard(String mobileNumber) {
        var optionalCard = repository.findByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistException("Card already registered for customer with mobile number: " + mobileNumber);
        }
        repository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardDTO fetchCard(String mobileNumber) {
        var optionalCard = repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        return CardsMapper.mapToCardDTO(optionalCard);
    }

    @Override
    public boolean updateCard(CardDTO cardDTO) {
        var fetchCard = repository.findByCardNumber(cardDTO.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardDTO.getCardNumber())
        );
        repository.save(CardsMapper.mapToCard(cardDTO, fetchCard));
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        repository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        repository.deleteByMobileNumber(mobileNumber);
        return true;
    }
}
