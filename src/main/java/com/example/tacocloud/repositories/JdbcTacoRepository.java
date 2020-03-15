package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;
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
public class JdbcTacoRepository implements TacoRepository {
    private SimpleJdbcInsert tacoInserter;
    private SimpleJdbcInsert tacoIngredientInserter;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.tacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Tacos").usingGeneratedKeyColumns("id");
        this.tacoIngredientInserter = new SimpleJdbcInsert(jdbc).withTableName("TacosIngredients");
    }

    @Override
    public Iterable<Taco> findAll() {
        return tacoInserter.getJdbcTemplate().query("select id, createdAt, name from Tacos", this::mapRow);
    }

    private Taco mapRow(ResultSet rs, int rowNum) throws SQLException {
        Taco taco = new Taco();
        taco.setId(rs.getLong("id"));
        taco.setCreatedAt(rs.getDate("createdAt"));
        taco.setName(rs.getString("name"));
        return taco;
    }

    @Override
    public Taco findById(int id) {
        return tacoInserter.getJdbcTemplate().queryForObject("select id, createdAt, name from Tacos where id=?", this::mapRow, id);
    }

    @Override
    public Taco save(Taco taco) {
        taco.setCreatedAt(new Date());
        long tacoId = saveToTacos(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveToTacosIngredients(tacoId, ingredient);
        }
        return taco;
    }

    private void saveToTacosIngredients(long tacoId, Ingredient ingredient) {
        Map<String, Object> map = new HashMap();
        map.put("ingredient_id", ingredient.getId());
        map.put("taco_id", tacoId);
        tacoIngredientInserter.execute(map);
    }

    private long saveToTacos(Taco taco) {
        Map<String, Object> map = new HashMap();
        map.put("createdAt", taco.getCreatedAt());
        map.put("name", taco.getName());
        return tacoInserter.executeAndReturnKey(map).longValue();
    }
}
