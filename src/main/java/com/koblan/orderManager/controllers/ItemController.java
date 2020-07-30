package com.koblan.orderManager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.services.ItemService;

@RestController
public class ItemController {
	
	 @Autowired
	 ItemService itemService;
	 
	 @GetMapping(value = "/items")
	 public ResponseEntity<List<Item>> getAllItems() throws NoSuchItemException {
	        List<Item> itemList = itemService.getAllItems();
	        return new ResponseEntity<>(itemList, HttpStatus.OK);
	 }

}
