package com.example.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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

 //   @Bean
//    CommandLineRunner runSeed(TableRepository tableRepository) {
//        return args -> {
//            for (int i = 1; i <= 4; i++) {
//                tableRepository.save(DinnerTable.builder()
//                        .tableName("t_2_" + i)
//                        .maxPersons((short) 2)
//                        .build());
//            }
//            for (int i = 1; i <= 7; i++) {
//                tableRepository.save(DinnerTable.builder()
//                        .tableName("t_5_" + i)
//                        .maxPersons((short) 5)
//                        .build());
//            }
//            for (int i = 1; i <= 2; i++) {
//                tableRepository.save(DinnerTable.builder()
//                        .tableName("t_10_" + i)
//                        .maxPersons((short) 10)
//                        .build());
//            }
//        };
//    }
}
