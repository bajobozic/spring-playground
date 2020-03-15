package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Taco;

public interface TacoRepository {
    Iterable<Taco> findAll();

    Taco findById(int id);

    Taco save(Taco taco);
}
