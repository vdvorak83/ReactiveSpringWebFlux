create table account (
    id bigint auto_increment,
    user_name varchar(50),
    balance int,
    primary key (id)

);

create table money_deposit_event (
    id bigint auto_increment,
    account_number bigint,
    amount int,
    foreign key (account_number) references account(id) on delete cascade,
    check (amount > 99)

);

insert into account
(user_name, balance)
values ( 'sam',0 ),
('mike',0),
('jake',0),
('mars',0)
