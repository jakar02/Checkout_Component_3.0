package com.example.Checkout_Component.service;

import com.example.Checkout_Component.model.DiscountWhenTogether;
import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.repository.DiscountWhenTogetherRepository;
import com.example.Checkout_Component.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private DiscountWhenTogetherRepository discountWhenTogetherRepository;

    private Map<Long, Integer> boughtItems = new HashMap<Long, Integer>();

    public ItemService(ItemRepository itemRepository, DiscountWhenTogetherRepository discountWhenTogetherRepository) {
        this.itemRepository = itemRepository;
        this.discountWhenTogetherRepository = discountWhenTogetherRepository;
    }

    public void scanItem(Long itemId) {
        boughtItems.put(itemId, boughtItems.getOrDefault(itemId, 0) +1);
    }

    public String getReceipt() {
        StringBuilder receipt = new StringBuilder("Receipt:\n");
        double total = 0.0;

        for (Map.Entry<Long, Integer> entry : boughtItems.entrySet()) {
            Optional<Item> item = itemRepository.findById(entry.getKey());
            if (item.isPresent()) {
                double price = calculateMultiPriced(item.get(), entry.getValue());
                total += price;
                receipt.append(String.format("%s x%d: %.2f\n", item.get().getName(), entry.getValue(), price));
            }
        }

        double discount = calculateBoughtTogetherDiscount();
        receipt.append(String.format("Discount: %.2f\n", discount));
        receipt.append(String.format("Total: %.2f", total - discount));

        boughtItems.clear();
        return receipt.toString();
    }


    public Double getTotalSum() {
        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : boughtItems.entrySet()) {
            Optional<Item> item = itemRepository.findById(entry.getKey());
            if (item.isPresent()) {
                total += calculateMultiPriced(item.get(), entry.getValue());
            }
        }
        total -= calculateBoughtTogetherDiscount();
        return total;
    }

    private double calculateMultiPriced(Item item, int quantity){
        double sets = Math.floor(quantity / (double) item.getRequiredQuantity());
        double remainder = quantity % item.getRequiredQuantity();
        return (double) item.getRequiredQuantity()*sets * item.getSpecialPrice() + remainder * item.getNormalPrice();
    }

    private double calculateBoughtTogetherDiscount() {
        double discount = 0.0;
        List<DiscountWhenTogether> discounts = discountWhenTogetherRepository.findAll();

        for (DiscountWhenTogether discountRule : discounts) {
            Long firstItemId = discountRule.getFirstItem().getId();
            Long secondItemId = discountRule.getSecondItem().getId();

            if (boughtItems.containsKey(firstItemId) && boughtItems.containsKey(secondItemId)) {
                int pairs = Math.min(boughtItems.get(firstItemId), boughtItems.get(secondItemId));
                discount += pairs * discountRule.getDiscountAmount();
            }
        }
        return discount;
    }

    public List<Item> getAvailableItems() {
        return itemRepository.findAll();
    }
}
