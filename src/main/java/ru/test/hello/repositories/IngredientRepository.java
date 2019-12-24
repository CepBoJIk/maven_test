package ru.test.hello.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.test.hello.models.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
