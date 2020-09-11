package com.koblan.orderManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.koblan.orderManager.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameIgnoreCase(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String username);
}