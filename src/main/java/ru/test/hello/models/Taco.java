package ru.test.hello.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class Taco {
    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "Имя доложно быть не меньше 5 символов, Петры идут мимо")
    private String name;

    @NotNull
    @Size(min = 1, message = "Выбери хотябы один ингредиент, че ты")
    private List<String> ingredients;
}
