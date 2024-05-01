create table if not exists users_details(
    user_id serial primary key,
    email varchar(50) unique,
    first_name varchar(50) not null check (length(trim(first_name)) > 2),
    last_name varchar(50) not null check (length(trim(last_name)) > 2),
    birthday date not null,
    address varchar(255),
    phone_number varchar(15) unique check(length(trim(phone_number)) > 3)
)