package com.ileven.tareas.seeders;

import com.ileven.tareas.models.User;
import com.ileven.tareas.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) { // Evita duplicados
            List<User> users = List.of(
                new User("Admin", "admin@example.com", passwordEncoder.encode("admin123")),
                new User("User1", "user1@example.com", "user123")
            );

            userRepository.saveAll(users);
            System.out.println("Usuarios insertados correctamente.");
        }
    }
}
