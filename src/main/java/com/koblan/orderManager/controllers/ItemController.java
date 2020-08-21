package com.koblan.orderManager.controllers;

import java.util.List;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.ItemName;
import com.koblan.orderManager.services.ItemService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {
	
	 @Autowired
	 ItemService itemService;
	 
	 @GetMapping(value = "/items")
	 public ResponseEntity<List<Item>> getAllItems() {
	        List<Item> itemList = itemService.getAllItems();
	        return new ResponseEntity<>(itemList, HttpStatus.OK);
	 }

         @GetMapping(value = "/items/names")
	 public ResponseEntity<List<ItemName>> getAllNames() {
	        List<ItemName> nameList = Arrays.asList(ItemName.values()); //Arrays.asList(...)
	        return new ResponseEntity<>(nameList, HttpStatus.OK);
	 }

         @GetMapping(value = "/items/{id}")
	 public ResponseEntity<Item> getItem(@PathVariable Long id) throws NoSuchItemException {
	        Item item=itemService.getItem(id);
                
	        return new ResponseEntity<>(item, HttpStatus.OK);
	 }

         @PostMapping(value="/items")
         public ResponseEntity<Item> addItem(@RequestBody Item item) {
	        Item newItem=itemService.createItem(item);
	        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
         }
	 
	 //Find Item by ItemName with minimal price or return all items if items are no more then five ones
	 @GetMapping(value = "/items/minprice/{itemName}")
	 public ResponseEntity<?> getItemWithMinimalPrice(@PathVariable ItemName itemName) throws NoSuchItemException {
	        List<Item> itemList = itemService.getAllItems();
	        if(itemList.size()<6) return new ResponseEntity<>(itemList, HttpStatus.OK);
	        else {Item minItem =itemList.stream().filter(e->e.getName()==itemName).min((x1,x2)->new Double(x1.getItemPrice()).compareTo(new Double(x2.getItemPrice()))).get();
	        return new ResponseEntity<>(minItem,HttpStatus.OK);
	        }
	 }
	 
	 @DeleteMapping("/items/{id}")
	 public ResponseEntity<?> deleteItem(@PathVariable Long id) throws NoSuchItemException {
			itemService.deleteItem(id);
			return new ResponseEntity(HttpStatus.OK);
     }

}
