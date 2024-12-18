package com.example.Checkout_Component.acceptance;

import com.example.Checkout_Component.controller.ItemController;
import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringJUnitConfig
class ItemControllerAcceptanceTest {

    @TestConfiguration
    static class Config {
        @Bean
        public ItemService itemService() {
            return Mockito.mock(ItemService.class);
        }

        @Bean
        public ItemController itemController(ItemService itemService) {
            return new ItemController(itemService);
        }
    }

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemController itemController;

    private MockMvc mockMvc;

    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();

        item1 = new Item("Apple", 10.0, 3, 25.0);
        item2 = new Item("Orange", 5.0, 2, 8.0);
    }

    @Test
    void getAvailableItems() throws Exception {
        when(itemService.getAvailableItems()).thenReturn(List.of(item1, item2));

        mockMvc.perform(get("/available_items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Apple"))
                .andExpect(jsonPath("$[1].name").value("Orange"));

        verify(itemService, times(1)).getAvailableItems();
    }

    @Test
    void scanItem() throws Exception {
        mockMvc.perform(post("/scan")
                        .param("itemId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Item added")));

        verify(itemService, times(1)).scanItem(1L);
    }

    @Test
    void actualPrice() throws Exception {
        when(itemService.getTotalSum()).thenReturn(15.0);

        mockMvc.perform(get("/total_sum"))
                .andExpect(status().isOk())
                .andExpect(content().string("15.0"));

        verify(itemService, times(1)).getTotalSum();
    }

    @Test
    void getReceipt() throws Exception {
        String receipt = """
                Receipt:
                Apple x1: 10.00
                Orange x2: 16.00
                Discount: 1.00
                Total: 25.00
                """;

        when(itemService.getReceipt()).thenReturn(receipt);

        mockMvc.perform(get("/receipt"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Receipt:")))
                .andExpect(content().string(containsString("Total: 25.00")));

        verify(itemService, times(1)).getReceipt();
    }
}