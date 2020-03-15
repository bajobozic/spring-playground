package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id,name,type from Ingredients", this::mapRow);
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject("select id,name,type from Ingredients where id=?", this::mapRow, id);
    }

    private Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("name"))
        );
    }
}
