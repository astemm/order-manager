package com.koblan.orderManager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.koblan.orderManager.models.ItemName;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.repositories.ItemRepository;

@SpringBootApplication
@EnableAsync
public class OrderManagerApplication {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@PostConstruct
	public void loadData() {
		itemRepo.save(new Item(ItemName.NOTEBOOK,3100.5));
		itemRepo.save(new Item(ItemName.NOTEBOOK,5400.0));
		itemRepo.save(new Item(ItemName.TABLET,4500.0));
		itemRepo.save(new Item(ItemName.SMARTPHONE,2800.0));
		itemRepo.save(new Item(ItemName.TABLET,3700.0));
		itemRepo.save(new Item(ItemName.NOTEBOOK,6500.0));
		itemRepo.save(new Item(ItemName.TABLET,2300.0));
		itemRepo.save(new Item(ItemName.SMARTPHONE,3300.0));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrderManagerApplication.class, args);
	}

}
