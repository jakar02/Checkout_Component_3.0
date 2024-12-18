package com.example.Checkout_Component.service;

import com.example.Checkout_Component.model.DiscountWhenTogether;
import org.junit.jupiter.api.Test;


import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.repository.DiscountWhenTogetherRepository;
import com.example.Checkout_Component.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private DiscountWhenTogetherRepository discountWhenTogetherRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void scanItem() {
        Long itemId = 1L;

        itemService.scanItem(itemId);
        itemService.scanItem(itemId);

        when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(new Item("Apple", 10.0, 3, 25.0)));

        assertEquals(20.0, itemService.getTotalSum());
    }

    @Test
    void getReceipt() {
        Long itemId1 = 1L;
        Long itemId2 = 2L;

        Item item1 = new Item("Apple", 10.0, 3, 25.0);
        Item item2 = new Item("Orange", 5.0, 2, 8.0);

        when(itemRepository.findById(itemId1)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(itemId2)).thenReturn(Optional.of(item2));

        itemService.scanItem(itemId1);
        itemService.scanItem(itemId1);
        itemService.scanItem(itemId2);

        String receipt = itemService.getReceipt();

        assertEquals(true, receipt.contains("Apple x2"));
        assertEquals(true, receipt.contains("Orange x1"));
        assertEquals(true, receipt.contains("Total"));
    }

    @Test
    void getTotalSumSpecialPrice() {
        Long itemId1 = 1L;
        Long itemId2 = 2L;

        Item item1 = new Item("Apple", 10.0, 3, 25.0);
        Item item2 = new Item("Orange", 5.0, 2, 8.0);

        when(itemRepository.findById(itemId1)).thenReturn(Optional.of(item1));
        when(itemRepository.findById(itemId2)).thenReturn(Optional.of(item2));

        itemService.scanItem(itemId1);
        itemService.scanItem(itemId2);
        itemService.scanItem(itemId2);

        double totalSum = itemService.getTotalSum();

        assertEquals(26.0, totalSum);
    }

}
