package com.koblan.orderManager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.Order;
import com.koblan.orderManager.repositories.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepo;
	
	public List<Item> getAllItems() {
        return itemRepo.findAll();
    }
	
	public Item getItem(Long id) throws NoSuchItemException {
        Item item = itemRepo.findById(id).get();
        if (item == null) throw new NoSuchItemException();
        return item;
    }
	
	@Transactional
    public Item createItem(Item item) {  //throws SuchItemExistsException {
	//	if (itemRepo.existsByName(item.getName()) && itemRepo.existsByItemPrice(item.getPrice()))
	//			throw new SuchItemExistsException();
        return itemRepo.save(item);
    }
	
	@Transactional
    public void deleteItem(Long id) throws NoSuchItemException {  
		Item item = itemRepo.findById(id).get();
        if (item == null) throw new NoSuchItemException();
        itemRepo.delete(item);
    }
	
	@Transactional
    public Item FindSuchItemByNameAndPrice(Item item) {
		Item item2=null;
		for(Item it:itemRepo.findAll()) {
			if (item.getName()==it.getName() && item.getPrice()==it.getPrice())
				{item2=it; break;}
		}
        return item2;
    }
	
	

}
