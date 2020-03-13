package com.bookstore.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.domain.UserPayment;

@Repository
@Transactional
public interface UserPaymentRepository extends CrudRepository<UserPayment, Integer> {

}
