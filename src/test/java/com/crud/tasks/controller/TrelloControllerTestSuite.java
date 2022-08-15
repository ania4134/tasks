package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloControllerTestSuite {

    @InjectMocks
    private TrelloController controller;

    @Mock
    private TrelloFacade facade;

    @Mock
    private TrelloValidator validator;

    @Test
    public void testCreateTrelloCard() {
        //given
        TrelloCardDto cardDto = new TrelloCardDto("card 1", "card description", "top", "12");
        TrelloCard mappedCard = new TrelloCard("mapped card no 1", "mapped card's description", "up", "12");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("12", "card no 1","url");
        validator.validateCard(mappedCard);
        when(facade.createCard(cardDto)).thenReturn(createdCard);

        //when
        ResponseEntity<CreatedTrelloCardDto> resultCard = controller.createTrelloCard(cardDto);

        //then
        Assertions.assertEquals("12", resultCard.getBody().getId());
    }

    @Test
    void shouldFetchEmptyList() {
        //given
        when(facade.fetchTrelloBoards()).thenReturn(List.of());

        //when
        ResponseEntity<List<TrelloBoardDto>> trelloBoardDtos = controller.getTrelloBoards();

        //then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.getBody().size()).isEqualTo(0);
    }
}
