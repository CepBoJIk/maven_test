package ru.test.hello.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Order {
    private Long id;

    private Date placedAt;

    @NotBlank(message = "Введите имя")
    private String name;

    @NotBlank(message = "Введите улицу")
    private String street;

    @NotBlank(message = "Введите город")
    private String city;
}
