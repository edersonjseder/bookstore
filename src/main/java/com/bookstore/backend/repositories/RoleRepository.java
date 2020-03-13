package com.bookstore.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.security.Role;

@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Integer>{

	Role findByName(String roleName);
	
}
