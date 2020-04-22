package com.example.tacocloud.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;

    @NonNull
    @Size(min = 3, message = "Name must be at least 3 character")
    private String name;
    @Size(min = 1, message = "Please enter name for Taco")
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

}
