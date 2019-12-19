package ru.test.hello.interfaces;

import ru.test.hello.models.Taco;
import ru.test.hello.models.TacoWithIngredients;

public interface TacoRepository {
    Taco save(Taco design);

    Iterable<TacoWithIngredients> getTacos();
}
