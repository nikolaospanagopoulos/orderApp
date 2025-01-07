package com.ordering.orderApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordering.orderApp.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
