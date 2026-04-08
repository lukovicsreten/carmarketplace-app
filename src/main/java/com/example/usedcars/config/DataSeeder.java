package com.example.usedcars.config;

import com.example.usedcars.entity.Ad;
import com.example.usedcars.entity.Car;
import com.example.usedcars.entity.Comment;
import com.example.usedcars.entity.User;
import com.example.usedcars.repository.AdRepository;
import com.example.usedcars.repository.CommentRepository;
import com.example.usedcars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Database already contains data — skipping seed.");
            return;
        }

        log.info("Seeding initial data...");

        // ── Users ──
        User user1 = userRepository.save(User.builder()
                .username("marko123")
                .email("marko@example.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("Marko")
                .lastName("Markovic")
                .phone("+381641234567")
                .role("USER")
                .build());

        User user2 = userRepository.save(User.builder()
                .username("jana_m")
                .email("jana@example.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("Jana")
                .lastName("Mitrovic")
                .phone("+381649876543")
                .role("USER")
                .build());

        User user3 = userRepository.save(User.builder()
                .username("nikola_p")
                .email("nikola@example.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("Nikola")
                .lastName("Petrovic")
                .phone("+381655551234")
                .role("ADMIN")
                .build());

        // ── Ads with Cars ──
        Car car1 = Car.builder()
                .brand("Volkswagen")
                .make("Volkswagen")
                .model("Golf 7")
                .vin("WVWZZZ1KZAW123456")
                .year(2018)
                .mileage(95000)
                .fuelType("Diesel")
                .transmission("Manual")
                .enginePower(150)
                .color("White")
                .build();

        Ad ad1 = adRepository.save(Ad.builder()
                .title("VW Golf 7 2.0 TDI - odlicno stanje")
                .description("Redovno servisiran, garaziran, prvi vlasnik u Srbiji. Non-smoker.")
                .price(new BigDecimal("14500.00"))
                .datePosted(LocalDate.now())
                .location("Beograd")
                .status("ACTIVE")
                .imageUrl("https://example.com/images/golf7.jpg")
                .user(user1)
                .car(car1)
                .build());

        Car car2 = Car.builder()
                .brand("BMW")
                .make("BMW")
                .model("320d")
                .vin("WBA8E1C55JA762345")
                .year(2016)
                .mileage(140000)
                .fuelType("Diesel")
                .transmission("Automatic")
                .enginePower(190)
                .color("Black")
                .build();

        Ad ad2 = adRepository.save(Ad.builder()
                .title("BMW 320d F30 - full oprema")
                .description("M paket, LED farovi, navigacija, kozna sedista. Uvezen iz Nemacke.")
                .price(new BigDecimal("17800.00"))
                .datePosted(LocalDate.now())
                .location("Novi Sad")
                .status("ACTIVE")
                .imageUrl("https://example.com/images/bmw320d.jpg")
                .user(user2)
                .car(car2)
                .build());

        Car car3 = Car.builder()
                .brand("Audi")
                .make("Audi")
                .model("A4 Avant")
                .vin("WAUZZZ8K9BA098765")
                .year(2020)
                .mileage(55000)
                .fuelType("Diesel")
                .transmission("Automatic")
                .enginePower(163)
                .color("Gray")
                .build();

        Ad ad3 = adRepository.save(Ad.builder()
                .title("Audi A4 Avant 2.0 TDI - kao nov")
                .description("Virtual cockpit, Matrix LED, Bang & Olufsen. Servisna knjiga.")
                .price(new BigDecimal("29900.00"))
                .datePosted(LocalDate.now())
                .location("Nis")
                .status("ACTIVE")
                .imageUrl("https://example.com/images/audia4.jpg")
                .user(user3)
                .car(car3)
                .build());

        // ── Comments ──
        commentRepository.save(Comment.builder()
                .content("Da li je moguca zamena za noviji model?")
                .text("Da li je moguca zamena za noviji model?")
                .datePosted(LocalDate.now())
                .user(user2)
                .ad(ad1)
                .build());

        commentRepository.save(Comment.builder()
                .content("Odlicna cena za ovo stanje! Jel moze dogovor za sutra?")
                .text("Odlicna cena za ovo stanje! Jel moze dogovor za sutra?")
                .datePosted(LocalDate.now())
                .rating(5)
                .user(user3)
                .ad(ad1)
                .build());

        commentRepository.save(Comment.builder()
                .content("Kolika je potrosnja na otvorenom putu?")
                .text("Kolika je potrosnja na otvorenom putu?")
                .datePosted(LocalDate.now())
                .user(user1)
                .ad(ad2)
                .build());

        commentRepository.save(Comment.builder()
                .content("Prelepo auto, svaka preporuka prodavcu!")
                .text("Prelepo auto, svaka preporuka prodavcu!")
                .datePosted(LocalDate.now())
                .rating(5)
                .user(user1)
                .ad(ad3)
                .build());

        log.info("Seed data inserted: 3 users, 3 ads (with cars), 4 comments.");
    }
}
