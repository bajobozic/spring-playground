package com.example.tacocloud.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Taco {
    private Long id;
    private Date createdAt;

    @NonNull
    @Size(min = 3, message = "Name must be at least 3 character")
    private String name;
    @Size(min = 1, message = "Please enter name for Taco")
    private List<Ingredient> ingredients;
}
