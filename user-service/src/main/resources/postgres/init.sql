create table users(
    id Serial primary key ,
    name varchar(50),
    balance int

);

create table user_transaction (
    id serial primary key,
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


/*drop table user_transaction;
drop table users;*/