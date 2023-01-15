package com.orange.restaurant;

import com.orange.restaurant.model.DinnerTable;
import com.orange.restaurant.model.User;
import com.orange.restaurant.repository.TableRepository;
import com.orange.restaurant.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner runSeed(TableRepository tableRepository, UserRepo userRepo) {
        //ToDo you will seed your database once per run, this would lead to duplicate seed data in the database
        // use database migration instead https://flywaydb.org/documentation/concepts/migrations.html
        return args -> {
            userRepo.save(User.builder()
                    .email("admin@gmail.com")
                    .mobileNumber("0123")
                    .password(passwordEncoder().encode("admin"))
                    .userType(Constants.ADMIN)
                    .build());
            for (int i = 1; i <= 4; i++) {
                tableRepository.save(DinnerTable.builder()
                        .tableName("t_2_" + i)
                        .maxPersons((short) 2)
                        .build());
            }
            for (int i = 1; i <= 7; i++) {
                tableRepository.save(DinnerTable.builder()
                        .tableName("t_5_" + i)
                        .maxPersons((short) 5)
                        .build());
            }
            for (int i = 1; i <= 2; i++) {
                tableRepository.save(DinnerTable.builder()
                        .tableName("t_10_" + i)
                        .maxPersons((short) 10)
                        .build());
            }
        };
    }
}
