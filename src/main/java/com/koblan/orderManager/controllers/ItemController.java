package com.koblan.orderManager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.ItemName;
import com.koblan.orderManager.services.ItemService;

@RestController
public class ItemController {
	
	 @Autowired
	 ItemService itemService;
	 
	 @GetMapping(value = "/items")
	 public ResponseEntity<List<Item>> getAllItems() {
	        List<Item> itemList = itemService.getAllItems();
	        return new ResponseEntity<>(itemList, HttpStatus.OK);
	 }
	 
	 //Find Item by ItemName with minimal price or return all items if items are no more then five ones
	 @GetMapping(value = "/items/{itemName}")
	 public ResponseEntity<?> getItemWithMinimalPrice(@PathVariable ItemName itemName) throws NoSuchItemException {
	        List<Item> itemList = itemService.getAllItems();
	        if(itemList.size()<6) return new ResponseEntity<>(itemList, HttpStatus.OK);
	        else {Item minItem =itemList.stream().filter(e->e.getName()==itemName).min((x1,x2)->new Double(x1.getPrice()).compareTo(new Double(x2.getPrice()))).get();
	        return new ResponseEntity<>(minItem,HttpStatus.OK);
	        }
	 }
	 
	 @DeleteMapping("/items/{id}")
	 public ResponseEntity<?> deleteItem(@PathVariable Long id) throws NoSuchItemException {
			itemService.deleteItem(id);
			return new ResponseEntity(HttpStatus.OK);
     }

}
