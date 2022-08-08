package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {

    @Test
    public void testMapToListDto() {
    //given
        List<TrelloList> listList = new ArrayList<>();
        TrelloList list1 = new TrelloList("123", "list no 1", true);
        TrelloList list2 = new TrelloList("145", "list no 2", false);
        TrelloList list3 = new TrelloList("148", "list no 3", false);
        listList.add(list1);
        listList.add(list2);
        listList.add(list3);

    //when
        TrelloMapper mapper = new TrelloMapper();
        List<TrelloListDto> resultList = mapper.mapToListDto(listList);

    //then
        Assertions.assertEquals(3, resultList.size());
        Assertions.assertEquals("list no 1", resultList.get(0).getName());
        Assertions.assertEquals("145", resultList.get(1).getId());
    }


    @Test
    public void testMapToBoards() {
        //given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloListDto list1 = new TrelloListDto("list1", "45632", true);
        TrelloListDto list2 = new TrelloListDto("list2", "45634", false);
        trelloListDtoList.add(list1);
        trelloListDtoList.add(list2);
        TrelloBoardDto board1 = new TrelloBoardDto("Kodilla Application", "456258", trelloListDtoList);
        trelloBoardDtoList.add(board1);

        //when
        TrelloMapper mapper = new TrelloMapper();
        List<TrelloBoard> resultList = mapper.mapToBoards(trelloBoardDtoList);

        //then
        Assertions.assertEquals(1, resultList.size());
        Assertions.assertEquals(2, resultList.get(0).getLists().size());
        Assertions.assertEquals("Kodilla Application", resultList.get(0).getName());
        Assertions.assertEquals(true, resultList.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void testMapToCardDto() {
        //given
        TrelloCard trelloCard = new TrelloCard("Card no 1", "card's description", "up", "4563");

        //when
        TrelloMapper mapper = new TrelloMapper();
        TrelloCardDto resultCard = mapper.mapToCardDto(trelloCard);

        //then
        Assertions.assertEquals("card's description", resultCard.getDescription());
        Assertions.assertEquals("4563", resultCard.getListId());
    }
}
