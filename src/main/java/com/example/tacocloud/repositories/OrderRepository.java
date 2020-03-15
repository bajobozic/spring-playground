package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Order;

public interface OrderRepository {
    Iterable<Order> findAll();

    Order findById(int id);

    Order save(Order order);
}
