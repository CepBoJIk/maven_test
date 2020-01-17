package ru.test.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.test.hello.models.User;
import ru.test.hello.repositories.UserRepository;

@Service
public class UserRepositoryUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return user;
        }

        throw  new UsernameNotFoundException("User '" + username + "' not found");
    }

}
