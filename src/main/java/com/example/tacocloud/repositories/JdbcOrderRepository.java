package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Order;
import com.example.tacocloud.models.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Orders").usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("OrdersTacos");
    }

    @Override
    public Iterable<Order> findAll() {
        return orderInserter.getJdbcTemplate().query("select * from Orders", this::rowMapper);
    }

    @Override
    public Order findById(int id) {
        return orderInserter.getJdbcTemplate().queryForObject("select * from Orders where id=?", this::rowMapper, id);
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveToOrders(order);
        order.setId(orderId);
        for (Taco taco : order.getTacos()) {
            saveToOrdersTacos(orderId, taco);
        }
        return order;
    }

    private void saveToOrdersTacos(long orderId, Taco taco) {
        Map<String, Long> map = new HashMap<>();
        map.put("order_id", orderId);
        map.put("taco_id", taco.getId());
        orderTacoInserter.execute(map);
    }

    private long saveToOrders(Order order) {
        Map<String, Object> map = new HashMap();
        map.put("id", order.getId());
        map.put("placedAt", order.getPlacedAt());
        map.put("name", order.getName());
        map.put("streetAddress", order.getStreetAddress());
        map.put("city", order.getCity());
        map.put("state", order.getState());
        map.put("zip", order.getZip());
        map.put("creditCard", order.getCreditCard());
        map.put("expiration", order.getExpiration());
        map.put("cvv", order.getCvv());

        return orderInserter.executeAndReturnKey(map).longValue();
    }

    private Order rowMapper(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setPlacedAt(rs.getDate("placedAt"));
        order.setName(rs.getString("name"));
        order.setStreetAddress(rs.getString("streetAddress"));
        order.setCity(rs.getString("city"));
        order.setState(rs.getString("state"));
        order.setZip(rs.getString("zip"));
        order.setCreditCard(rs.getString("creditCard"));
        order.setExpiration(rs.getString("expiration"));
        order.setCvv(rs.getString("cvv"));
        return order;
    }
}
