package com.koblan.orderManager.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.koblan.orderManager.models.*;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Order findById(long id);
}
