package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
