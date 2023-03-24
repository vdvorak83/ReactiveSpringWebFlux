create table users (
    id bigint auto_increment,
    name varchar(50),
    balance int,
    created_by varchar(50),
    primary key (id)

);

create table user_transaction (
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    foreign key (user_id) references users(id) on delete cascade

);

insert into users
(name,balance)
values ( 'sam',100 ),
('mike',1000),
('jake',800),
('mars',2000)
