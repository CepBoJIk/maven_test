package ru.test.hello.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TacoWithIngredients {
    private Long id;

    private Date createdAt;

    private String name;

    private List<Ingredient> ingredients;
}
