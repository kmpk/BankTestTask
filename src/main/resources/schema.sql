create schema if not exists bank;

create table if not exists bank.user
(
    id         serial primary key,
    birth_date date         not null,
    email      varchar(255)
        constraint user_email_key unique,
    full_name  varchar(255) not null,
    login      varchar(255) not null
        constraint user_login_key unique,
    password   varchar(255) not null,
    phone      varchar(255)
        constraint user_phone_key unique
);

create index if not exists user_full_name_index on bank.user (full_name varchar_pattern_ops);

create table if not exists bank.account
(
    id              integer not null
        primary key
        constraint account_id_user_id_fkey
            references bank.user,
    balance         numeric(38, 2)
        constraint account_balance_check check (balance >= 0),
    initial_balance numeric(38, 2)
);



