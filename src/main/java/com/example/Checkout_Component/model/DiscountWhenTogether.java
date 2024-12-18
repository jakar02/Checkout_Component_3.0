package com.example.Checkout_Component.model;

import com.example.Checkout_Component.repository.ItemRepository;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
public class DiscountWhenTogether {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_item_id")
    private Item firstItem;

    @ManyToOne
    @JoinColumn(name = "second_item_id")
    private Item secondItem;

    @JoinColumn(name = "discountAmount")
    private double discountAmount;

    public DiscountWhenTogether() {

    }

    public DiscountWhenTogether(Item item1, Item item2, double v) {
        firstItem = item1;
        secondItem = item2;
        discountAmount = v;
    }

    public Item getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(Item firstItem) {
        this.firstItem = firstItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Item getSecondItem() {
        return secondItem;
    }

    public void setSecondItem(Item secondItem) {
        this.secondItem = secondItem;
    }

}
