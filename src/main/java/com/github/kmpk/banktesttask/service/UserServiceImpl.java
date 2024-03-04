package com.github.kmpk.banktesttask.service;

import com.github.kmpk.banktesttask.exception.AppException;
import com.github.kmpk.banktesttask.mapper.PageMapper;
import com.github.kmpk.banktesttask.mapper.UserMapper;
import com.github.kmpk.banktesttask.model.User;
import com.github.kmpk.banktesttask.repository.UserRepository;
import com.github.kmpk.banktesttask.security.AuthUser;
import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.PageResultTo;
import com.github.kmpk.banktesttask.to.UserTo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new AuthUser(repository.findByLoginIgnoreCase(login)
                .orElseThrow(() -> new UsernameNotFoundException("User with login " + login + " not found")));
    }

    @Override
    @Transactional
    public UserTo createUser(CreateUserRequestTo request) {
        User newUser = userMapper.toEntity(request);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        return userMapper.toTo(repository.save(newUser));
    }

    @Override
    @Transactional
    public void editEmail(int id, String newEmailOrNull) {
        User user = repository.getReferenceById(id);
        if (newEmailOrNull == null) {
            if (user.getPhone() == null) {
                throw new AppException("Can't delete email while phone is not set");
            }
            user.setEmail(null);
        } else {
            Optional<Integer> userIdWithThisEmail = repository.findByEmailIgnoreCase(newEmailOrNull).map(User::getId);
            if (userIdWithThisEmail.isPresent() && userIdWithThisEmail.get() != id) {
                throw new AppException("This email is already taken by another user");
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
                throw new AppException("Can't delete phone while email is not set");
            }
            user.setPhone(null);
        } else {
            Optional<Integer> userIdWithThisPhone = repository.findByPhone(newPhoneOrNull).map(User::getId);
            if (userIdWithThisPhone.isPresent() && userIdWithThisPhone.get() != id) {
                throw new AppException("This phone number is already taken by another user");
            }
            user.setPhone(newPhoneOrNull);
        }
    }

    @Override
    public PageResultTo findAllAfterBirthDate(LocalDate birthDate, String[] sort, int size, int page) {
        List<Sort.Order> orders = parseOrders(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        return pageMapper.toTo(repository.findAllByBirthDateAfter(birthDate, pagingSort).map(userMapper::toTo));
    }

    @Override
    public Optional<UserTo> findByPhone(String phone) {
        return repository.findByPhone(phone).map(userMapper::toTo);
    }

    @Override
    public Optional<UserTo> findByEmailIgnoreCase(String email) {
        return repository.findByEmailIgnoreCase(email).map(userMapper::toTo);
    }

    @Override
    public PageResultTo findAllByFullNameLike(String fullName, String[] sort, int size, int page) {
        List<Sort.Order> orders = parseOrders(sort);
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        return pageMapper.toTo(repository.findAllByFullNameLike(fullName, pagingSort).map(userMapper::toTo));
    }

    private static List<Sort.Order> parseOrders(String[] sort) {
        if (sort.length % 2 != 0) {
            throw new AppException("The number of sorting parameters must be even.");
        }
        List<Sort.Order> orders = new ArrayList<>();
        String currentSortField = null;
        for (String s : sort) {
            if (currentSortField == null) {
                currentSortField = s;
            } else {
                Sort.Direction direction = s.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
                orders.add(new Sort.Order(direction, currentSortField));
                currentSortField = null;
            }
        }
        return orders;
    }
}
