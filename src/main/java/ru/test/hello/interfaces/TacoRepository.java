package ru.test.hello.interfaces;

import ru.test.hello.models.Taco;

public interface TacoRepository {
    Taco save(Taco design);

    Iterable<Taco> getTacos();
}
