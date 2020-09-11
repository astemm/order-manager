package com.koblan.orderManager.testingClasses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koblan.orderManager.OrderManagerApplication;
import com.koblan.orderManager.models.Item;
import com.koblan.orderManager.models.Order;
import com.koblan.orderManager.repositories.ItemRepository;
import com.koblan.orderManager.repositories.OrderRepository;

@AutoConfigureMockMvc
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderManagerApplication.class)
public class ItemControllerTest {
	
	   @Autowired
	   private MockMvc mockMvc;
	   
	   @Autowired
	   private ItemRepository itemRepo;
	   
	   private String mapToJson(Object obj) throws JsonProcessingException {
		      ObjectMapper objectMapper = new ObjectMapper();
		      return objectMapper.writeValueAsString(obj);
	  }
	  
	  private <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
	          ObjectMapper objectMapper = new ObjectMapper();
	          return objectMapper.readValue(json, clazz);
	  }
	  
	   //@Ignore
	   @WithMockUser()
	   @Test
	   public void getItemList() throws Exception {
	      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/items").contentType(MediaType.APPLICATION_JSON_VALUE)
	    		  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		  int status = mvcResult.getResponse().getStatus();
		  assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      Item[] itemlist = mapFromJson(content, Item[].class);
	      assertTrue("Number of Items retrived",itemlist.length == 8);
	   }
	   
	   //@Ignore
	   @WithMockUser()
	   @Test
	   public void getItemWithMinimalPrice() throws Exception {
	      MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/items/minprice/NOTEBOOK").contentType(MediaType.APPLICATION_JSON_VALUE)
	    		  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		  int status = mvcResult.getResponse().getStatus();
		  assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      Item minItem = mapFromJson(content, Item.class);
	      assertTrue("The minimal price of NOTEBOOKS are 3100.5",minItem.getItemPrice() == 3100.5);
	      
	      mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/items/minprice/TABLET").contentType(MediaType.APPLICATION_JSON_VALUE)
	    		  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		  status = mvcResult.getResponse().getStatus();
		  assertEquals(200, status);
	      content = mvcResult.getResponse().getContentAsString();
	      minItem = mapFromJson(content, Item.class);
	      assertTrue("The minimal price of TABLETS are 2300",minItem.getItemPrice() == 2300);
	   }
	   
	   @WithMockUser()
	   @Test
	   public void CheckItemWithMinimalPriceWhenItReturnsListOfItems() throws Exception {
		  this.mockMvc.perform(MockMvcRequestBuilders.delete("/items/6")).andReturn();
		  this.mockMvc.perform(MockMvcRequestBuilders.delete("/items/7")).andReturn();
		  this.mockMvc.perform(MockMvcRequestBuilders.delete("/items/8")).andReturn();
		  
		  MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/items/minprice/NOTEBOOK").contentType(MediaType.APPLICATION_JSON_VALUE)
	    		  .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		  int status = mvcResult.getResponse().getStatus();
		  assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      Item[] itemlist = mapFromJson(content, Item[].class);
	      assertTrue("Number of Items retrived",itemlist.length == 5);
	      
	   }
	      

}
