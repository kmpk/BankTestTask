package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.mapper.UserMapper;
import com.github.kmpk.banktesttask.model.User;
import com.github.kmpk.banktesttask.repository.UserRepository;
import com.github.kmpk.banktesttask.security.AuthUser;
import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.UserTo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new AuthUser(repository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found")));
    }

    @Override
    @Transactional
    public UserTo createUser(CreateUserRequestTo request) {
        User newUser = mapper.toEntity(request);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        return mapper.toTo(repository.save(newUser));
    }

    @Override
    public void editEmail(int id, String newEmailOrNull) {

    }

    @Override
    public void editPhone(int id, String newPhoneOrNull) {

    }

    @Override
    public Page<UserTo> findAllAfterBirthDate(LocalDate birthDate, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<UserTo> findByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<UserTo> findByEmailIgnoreCase(String email) {
        return Optional.empty();
    }

    @Override
    public Page<UserTo> findAllByFullNameLike(String fullName, Pageable pageable) {
        return null;
    }
}
