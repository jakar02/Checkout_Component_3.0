package com.example.Checkout_Component.controller;



import com.example.Checkout_Component.controller.ItemController;
import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAvailableItems() {
        Item item1 = new Item("Apple", 10.0, 3, 25.0);
        Item item2 = new Item("Orange", 5.0, 2, 8.0);

        when(itemService.getAvailableItems()).thenReturn(List.of(item1, item2));

        ResponseEntity<List<Item>> response = itemController.getAvailableItems();

        assertEquals(2, response.getBody().size());
        assertEquals("Apple", response.getBody().get(0).getName());
        assertEquals("Orange", response.getBody().get(1).getName());
    }

    @Test
    void scanItem() {
        Long itemId = 1L;

        ResponseEntity<String> response = itemController.scanItem(itemId);

        assertEquals("Item added", response.getBody());
        verify(itemService, times(1)).scanItem(itemId);
    }

    @Test
    void actualPrice() {
        double expectedPrice = 15.0;

        when(itemService.getTotalSum()).thenReturn(expectedPrice);

        ResponseEntity<String> response = itemController.actualPrice();

        assertEquals("15.0", response.getBody());
    }

    @Test
    void getReceipt() {
        // Przygotowanie danych
        String expectedReceipt = "Receipt: Apple x1: 10.0\nOrange x2: 12.0\nDiscount: 1.0\nTotal: 21.0";

        when(itemService.getReceipt()).thenReturn(expectedReceipt);

        ResponseEntity<String> response = itemController.getReceipt();

        assertEquals(expectedReceipt, response.getBody());
    }
}
