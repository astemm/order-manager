package com.koblan.orderManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.ItemName;

public interface ItemRepository extends JpaRepository<Item, Long> {
	Item findById(long id);
	//Item findByName(ItemName name);
	//Item findByItemPrice(double itemPrice);
	boolean existsById(long id);
	boolean existsByName(ItemName name);
	boolean existsByItemPrice(double itemPrice);
}
