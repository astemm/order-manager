package com.koblan.orderManager.services;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koblan.orderManager.exceptions.NoSuchItemException;
import com.koblan.orderManager.exceptions.NoSuchOrderException;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.Order;
import com.koblan.orderManager.repositories.ItemRepository;
import com.koblan.orderManager.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	ItemService itemService;
	
	public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
	
	public Order getOrder(Long id) throws NoSuchOrderException {
        Order order = orderRepo.findById(id).get();
        if (order == null) throw new NoSuchOrderException();
        return order;
    }
	
	@Transactional
    public Order createOrder(Order order) throws NoSuchItemException {
		 if (itemRepo.existsById(order.getItem().getId())==false) { //Check if nested order's item is present in storage
			 Item item1=itemService.FindSuchItemByNameAndPrice(order.getItem());
			 if (item1==null) { //Do not insert if the name and price already exists
			 Item item2=itemRepo.save(order.getItem()); //If not insert this one
			 order.setItem(item2);}
			 else order.setItem(item1);
			 ;}; //Assigning new item to Order with generated id
		 Item item=order.getItem();
		 Set<Order> orders=item.getOrders();
		 orders.add(order);
		 item.setOrders(orders);
		 return orderRepo.save(order);
    }
	
	@Transactional
    public void updateOrder(Order nOrder, Long id) throws NoSuchOrderException, NoSuchItemException{
		Order order = orderRepo.findById(id).get();
        if (order == null) throw new NoSuchOrderException();
        if (itemRepo.existsById(nOrder.getItem().getId())==false) throw new NoSuchItemException();
        order.setPrice(nOrder.getPrice());
        order.setQuantity(nOrder.getQuantity());
        order.setItem(nOrder.getItem());
        orderRepo.save(order);
    }
	
	@Transactional
    public void deleteOrder(Long id) throws NoSuchOrderException {
		Order order = orderRepo.findById(id).get();
        if (order == null) throw new NoSuchOrderException();
        orderRepo.delete(order);
    }
	
	@Async
	@Transactional
	public void deleteOrderAsync(Long id) throws NoSuchOrderException,InterruptedException {
		     long start=System.currentTimeMillis();
	         new Timer().schedule(new TimerTask() {public void run() {
			 try{
			 System.out.println(Thread.currentThread().getName());
			 deleteOrder(id);
			 System.out.println(System.currentTimeMillis()-start);
			 }
			 catch (Exception ex) {}
			 }},600000);// Run Timertask After 10 minutes - 600 seconds
	         
	 /* Alternative waiting of 10 minutes to delete order 
		long start=System.currentTimeMillis();
		Thread.sleep(180000);
		System.out.println(System.currentTimeMillis()-start);
		System.out.println(Thread.currentThread().getName());
		deleteOrder(id); */
	}

}
