package com.example.Checkout_Component;

import com.example.Checkout_Component.model.DiscountWhenTogether;
import com.example.Checkout_Component.model.Item;
import com.example.Checkout_Component.repository.DiscountWhenTogetherRepository;
import com.example.Checkout_Component.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CheckoutComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckoutComponentApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ItemRepository itemRepository, DiscountWhenTogetherRepository discountWhenTogetherRepository) {
		return args -> {
			Item item1 = new Item("A", 40.0, 3, 30.0);
			Item item2 = new Item("B", 10.0, 2, 7.5);
			Item item3 = new Item("C", 30.0, 4, 20.0);
			Item item4 = new Item("D", 25.0, 2, 23.5);

			itemRepository.save(item1);
			itemRepository.save(item2);
			itemRepository.save(item3);
			itemRepository.save(item4);

			DiscountWhenTogether discount1 = new DiscountWhenTogether(item1, item2, 1.0);
			DiscountWhenTogether discount2 = new DiscountWhenTogether(item2, item3, 2.0);

			discountWhenTogetherRepository.save(discount1);
			discountWhenTogetherRepository.save(discount2);
		};
	}
}
