package com.github.kmpk.banktesttask.mapper;

import com.github.kmpk.banktesttask.model.Account;
import com.github.kmpk.banktesttask.model.User;
import com.github.kmpk.banktesttask.to.CreateUserRequestTo;
import com.github.kmpk.banktesttask.to.UserTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = Account.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", expression = "java(new Account(requestTo.initialBalance(),requestTo.initialBalance()))")
    User toEntity(CreateUserRequestTo requestTo);

    UserTo toTo(User user);
}
