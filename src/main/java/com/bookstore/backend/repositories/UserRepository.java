package com.bookstore.backend.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.domain.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
    
    Set<User> findAll();
    
    @Modifying // Indicates to JPA engine that the content of the @Query annotation will change the database state
    @Query("update User user set user.password =:password where user.id =:userId")
    void updateUserPassword(@Param("userId") int userId, @Param("password") String password);

    @Modifying // Indicates to JPA engine that the content of the @Query annotation will change the database state
    @Query("delete User user where user.username =:username")
    void deleteUserByUsername(@Param("username") String username);
    
    void deleteAccountById(Integer id);
}
