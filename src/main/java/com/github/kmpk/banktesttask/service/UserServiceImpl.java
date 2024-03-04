package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.exception.AppValidationException;
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
    @Transactional
    public void editEmail(int id, String newEmailOrNull) {
        User user = repository.getReferenceById(id);
        if (newEmailOrNull == null) {
            if (user.getPhone() == null) {
                throw new AppValidationException("Can't delete email while phone is not set");
            }
            user.setEmail(null);
        } else {
            Optional<Integer> userIdWithThisEmail = repository.findByEmailIgnoreCase(newEmailOrNull).map(User::getId);
            if (userIdWithThisEmail.isPresent() && userIdWithThisEmail.get() != id) {
                throw new AppValidationException("This email is already taken by another user");
            }
            user.setEmail(newEmailOrNull);
        }
    }

    @Override
    @Transactional
    public void editPhone(int id, String newPhoneOrNull) {
        User user = repository.getReferenceById(id);
        if (newPhoneOrNull == null) {
            if (user.getEmail() == null) {
                throw new AppValidationException("Can't delete phone while email is not set");
            }
            user.setPhone(null);
        } else {
            Optional<Integer> userIdWithThisPhone = repository.findByPhone(newPhoneOrNull).map(User::getId);
            if (userIdWithThisPhone.isPresent() && userIdWithThisPhone.get() != id) {
                throw new AppValidationException("This phone number is already taken by another user");
            }
            user.setPhone(newPhoneOrNull);
        }
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
