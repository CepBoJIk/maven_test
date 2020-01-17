package ru.test.hello.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.test.hello.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
