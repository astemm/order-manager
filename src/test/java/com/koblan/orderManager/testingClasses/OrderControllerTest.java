package com.koblan.orderManager.testingClasses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koblan.orderManager.OrderManagerApplication;
import com.koblan.orderManager.controllers.ItemController;
import com.koblan.orderManager.controllers.OrderController;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.ItemName;
import com.koblan.orderManager.models.Order;
import com.koblan.orderManager.repositories.ItemRepository;
import com.koblan.orderManager.repositories.OrderRepository;
import com.koblan.orderManager.services.ItemService;
import com.koblan.orderManager.services.OrderService;

@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderManagerApplication.class)
public class OrderControllerTest {
	
	   @Autowired
	   private MockMvc mockMvc;
	   
	   @Autowired
	   private OrderRepository orderRepo;
	   
	   @Autowired
	   private ItemRepository itemRepo;
	   
	   @BeforeClass
	   static public void setUp() {
	   }
	   
	   private String mapToJson(Object obj) throws JsonProcessingException {
		      ObjectMapper objectMapper = new ObjectMapper();
		      return objectMapper.writeValueAsString(obj);
	  }
	  
	  private <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
	          ObjectMapper objectMapper = new ObjectMapper();
	          return objectMapper.readValue(json, clazz);
	  }
	   
	   @Test
	   public void createOrderandDelete() throws Exception {
		   Item item=itemRepo.findById(1L);
		   Order order=new Order(2,item);
		   MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders/temp").contentType(MediaType.APPLICATION_JSON_VALUE)
			         .content(mapToJson(order))).andReturn();
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(201, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   Order order11=mapFromJson(content, Order.class);
		   assertEquals(2,order11.getQuantity());
		   Thread.sleep(650000);//Wait more than assigned 10 minutes to check if async delete order run correctly
		   assertEquals(0,orderRepo.findAll().size());
		   
	   }
	   
	   //@Ignore
	   @Test
	   public void createOrder() throws Exception {
		   Item item1=itemRepo.findById(2L);
		   Item item2=itemRepo.findById(3L);
		   Order order1=new Order(3,item1);
		   Order order2=new Order(1,item2);
		   MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON_VALUE)
			         .content(mapToJson(order1))).andReturn();
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(201, status);
		   String content = mvcResult.getResponse().getContentAsString();
		   Order order11=mapFromJson(content, Order.class);
		   System.out.println("id"+order11.getId());
		   assertEquals(3,order11.getQuantity());
		   assertEquals("items names equals",order11.getItem().getName(), order1.getItem().getName());
		   mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON_VALUE)
			         .content(mapToJson(order2))).andReturn();
		   status = mvcResult.getResponse().getStatus();
		   assertEquals(201, status);
		   content = mvcResult.getResponse().getContentAsString();
		   Order order22=mapFromJson(content, Order.class);
		   assertEquals(1,order22.getQuantity());
	   }
	   
	   //@Ignore
	   @Test
	   public void getOrderList() throws Exception {
	      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/orders").contentType(MediaType.APPLICATION_JSON_VALUE)
	    		  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		   int status = mvcResult.getResponse().getStatus();
		   assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      Order[] orderlist = mapFromJson(content, Order[].class);
	      assertTrue("Number of Orders retrived",orderlist.length == 2);
	      assertEquals(3,orderlist[0].getQuantity());
	      assertEquals(1,orderlist[1].getQuantity());
	   }
	   
	   //@Ignore
	   @Test
	   public void removeOrder() throws Exception {
	      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/1")).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals("Deleted", 200, status);
	      mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/orders/2")).andReturn();
	      status = mvcResult.getResponse().getStatus();
	      assertEquals("Deleted", 200, status);
	      assertEquals(0,orderRepo.findAll().size());
	   }

	   
}
