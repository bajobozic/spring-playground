package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
