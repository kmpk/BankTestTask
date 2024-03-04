package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.UserTo;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserTo createUser(@Valid CreateUserRequestTo request);

    void editEmail(int id, String newEmailOrNull);

    void editPhone(int id, String newPhoneOrNull);

    Page<UserTo> findAllAfterBirthDate(LocalDate birthDate, Pageable pageable);

    Optional<UserTo> findByPhone(String phone);

    Optional<UserTo> findByEmailIgnoreCase(String email);

    Page<UserTo> findAllByFullNameLike(String fullName, Pageable pageable);
}
