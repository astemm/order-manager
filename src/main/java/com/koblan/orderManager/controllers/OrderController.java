package com.koblan.orderManager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Order;
import com.koblan.orderManager.services.OrderService;

@RestController
public class OrderController {
	
	 @Autowired
	 OrderService orderService;
	 
	 @GetMapping(value = "/orders")
	 public ResponseEntity<List<Order>> getAllOrders() throws NoSuchOrderException {
	        List<Order> orderList = orderService.getAllOrders();
	        return new ResponseEntity<>(orderList, HttpStatus.OK);
	 }
	 

	 @GetMapping(value = "/orders/{id}")
	 public ResponseEntity<Order> getUser(@PathVariable Long id) throws NoSuchOrderException {
	        Order order=orderService.getOrder(id);
	        return new ResponseEntity<>(order, HttpStatus.OK);
	 }
	 
	 @PostMapping(value="/orders")
     public ResponseEntity<Order> addOrder(@RequestBody Order order) throws NoSuchItemException {
			 Order newOrder=orderService.createOrder(order);
			 return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
     }
	 
	 //Main rest method that create order and then remove it from storage after 10 minutes in async method
	 @PostMapping(value="/orders/temp")
     public ResponseEntity<Order> addOrderAndDelete(@RequestBody Order order) throws NoSuchOrderException,InterruptedException, NoSuchItemException {
			 Order newOrder=orderService.createOrder(order);
			 orderService.deleteOrderAsync(newOrder.getId());
			 return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
     }
	 
	 @PutMapping(value = "/orders/{id}")
	 public  ResponseEntity<Order> updateOrder(@RequestBody Order order, @PathVariable Long id) throws NoSuchOrderException, NoSuchItemException {
	        orderService.updateOrder(order, id);
	        return new ResponseEntity<>(HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/orders/{id}")
	 public ResponseEntity<?> deleteOrder(@PathVariable Long id) throws NoSuchOrderException {
			orderService.deleteOrder(id);
			return new ResponseEntity(HttpStatus.OK);
     }
	
	

}
