package ru.test.hello.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.test.hello.models.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
