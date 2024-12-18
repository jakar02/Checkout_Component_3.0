package com.example.Checkout_Component.controller;

import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/available_items")
    public ResponseEntity<List<Item>> getAvailableItems() {
        return ResponseEntity.ok().body(itemService.getAvailableItems());
    }

    @PostMapping("/scan")
    public ResponseEntity<String> scanItem(@RequestParam Long itemId){
        itemService.scanItem(itemId);
        return ResponseEntity.ok().body("Item added");
    }

    @GetMapping("/total_sum")
    public ResponseEntity<String> actualPrice(){
        Double sum = itemService.getTotalSum();
        return ResponseEntity.ok().body(sum.toString());
    }

    @GetMapping("/receipt")
    public ResponseEntity<String> getReceipt(){
        String receipt = itemService.getReceipt();
        return ResponseEntity.ok().body(receipt);
    }

}
