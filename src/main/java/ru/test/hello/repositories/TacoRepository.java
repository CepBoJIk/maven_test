package ru.test.hello.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.test.hello.models.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
