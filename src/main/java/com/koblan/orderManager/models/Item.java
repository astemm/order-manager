package com.koblan.orderManager.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "items")
public class Item {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private long id;
      private ItemName name;
	  private double itemPrice;
	  
	  @JsonBackReference
	  @OneToMany(mappedBy="item",fetch = FetchType.LAZY)
	  private Set<Order> orders=new HashSet<Order>();

	  public Item() {}

	  public Item(ItemName name, double itemPrice) {
		this.name=name;
	    this.itemPrice=itemPrice;
	  }
	  
	  public long getId() {
		return id;
	  }

	  public void setId(long id) {
		this.id = id;
	 }

	 public void setName(ItemName name) {
		this.name = name;
	  }

	  public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	  }

	  public double getPrice() {
			return itemPrice;
	  }
	  
	  public ItemName getName() {
			return name;
	  }

	  public Set<Order> getOrders() {
		    return orders;
	  }

	  public void setOrders(Set<Order> orders) {
		    this.orders = orders;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(itemPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (name != other.name)
			return false;
		if (Double.doubleToLongBits(itemPrice) != Double.doubleToLongBits(other.itemPrice))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", itemPrice=" + itemPrice + "]";
	}
	
	

}
