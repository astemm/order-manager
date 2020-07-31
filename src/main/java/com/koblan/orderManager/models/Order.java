package com.koblan.orderManager.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

  @Id 
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  private double price;
  private int quantity;

  @ManyToOne
  @JoinColumn(name="item_id", nullable=false)
  private Item item;

  public Order() {}

  public Order(int quantity, Item item) {
    //this.price=price;
	this.price =item.getItemPrice()*quantity;
    this.quantity=quantity;
    this.item = item;
  }
  
  public long getId() {
		return id;
	  }

  public double getPrice() {
	return price;
  }

  public void setPrice(double price) {
		this.price =price;
	  }
  
  public void setPrice() {
	this.price =item.getItemPrice()*quantity;
  }

  public int getQuantity() {
	return quantity;
  }

  public void setQuantity(int quantity) {
	this.quantity = quantity;
  }

  public Item getItem() {
	return item;
  }

  public void setItem(Item item) {
	this.item = item;
  }

  @Override
  public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((item == null) ? 0 : item.hashCode());
	long temp;
	temp = Double.doubleToLongBits(price);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + quantity;
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
	Order other = (Order) obj;
	if (id != other.id)
		return false;
	if (item == null) {
		if (other.item != null)
			return false;
	} else if (!item.equals(other.item))
		return false;
	if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
		return false;
	if (quantity != other.quantity)
		return false;
	return true;
  }
  
  
}
