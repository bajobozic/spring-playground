package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();

    Ingredient findById(String id);
}
