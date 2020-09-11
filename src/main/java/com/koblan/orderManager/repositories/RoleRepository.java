package com.koblan.orderManager.repositories;

import com.koblan.orderManager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByRoleIgnoreCase(String role);
}

