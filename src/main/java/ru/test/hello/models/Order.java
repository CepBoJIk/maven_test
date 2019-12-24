package ru.test.hello.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date placedAt;

    @NotBlank(message = "Введите имя")
    private String name;

    @NotBlank(message = "Введите улицу")
    private String street;

    @NotBlank(message = "Введите город")
    private String city;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    public void addDesign(Taco taco) {
        tacos.add(taco);
    }

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
