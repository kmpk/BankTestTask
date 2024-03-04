package com.github.kmpk.banktesttask.repository;

import com.github.kmpk.banktesttask.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAllByBirthDateAfter(LocalDate birthDate, Pageable pageable);

    Optional<User> findByPhone(String phone);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("Select u from User u where u.fullName like ?1%")
    Page<User> findAllByFullNameLike(String fullName, Pageable pageable);

    Optional<User> findByLoginIgnoreCase(String login);
}