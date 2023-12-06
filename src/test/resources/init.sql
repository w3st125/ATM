create table bank_user
(
    user_id    bigint      not null
        constraint users_pkey
            primary key,
    user_pass  varchar(32) not null,
    user_login varchar(16) not null
        constraint users_user_login_key
            unique
        constraint user_login_must_be_lower_case
            check ((user_login)::text = lower((user_login)::text))
);

alter table bank_user
    owner to postgres;

create table transaction_type
(
    txnt_id   bigint      not null
        constraint txn_type_pk
            primary key,
    txnt_name varchar(32) not null
        constraint txn_type_unique
            unique
);

alter table transaction_type
    owner to postgres;

create table currency
(
    cur_id            bigint not null
        constraint currency_pk
            primary key,
    cur_name          varchar(16),
    cur_exchange_rate numeric
);

alter table currency
    owner to postgres;

create table account
(
    acc_user_id     bigint         not null
        constraint accounts_to_users_fkey
            references bank_user,
    acc_balance     numeric(20, 2) not null,
    acc_id          bigint         not null
        constraint account_pk
            primary key,
    acc_number      varchar(20),
    acc_currency_id bigint
        constraint account_currency_cur_id_fk
            references currency
);

alter table account
    owner to postgres;

create table transaction
(
    txn_id           bigserial
        constraint transaction_pk
            primary key,
    txn_account_from varchar(16),
    txn_account_to   varchar(16),
    txn_date         timestamp      not null,
    txn_type_id      bigint         not null
        constraint transaction_transaction_type_txnt_id_fk
            references transaction_type,
    txn_amount       numeric(20, 2) not null,
    txn_currency_id  bigint
        constraint transaction_currency_cur_id_fk
            references currency
);

alter table transaction
    owner to postgres;

create view new_view(txn_id, txn_date, txn_amount, txnt_name, user_from, user_to) as
SELECT transaction.txn_id,
       transaction.txn_date,
       transaction.txn_amount,
       tt.txnt_name,
       buu.user_login AS user_from,
       bu.user_login  AS user_to
FROM transaction
         JOIN transaction_type tt ON transaction.txn_type_id = tt.txnt_id
         LEFT JOIN account a ON transaction.txn_account_to::text = a.acc_number::text
         LEFT JOIN account b ON transaction.txn_account_from::text = b.acc_number::text
         LEFT JOIN bank_user bu ON bu.user_id = a.acc_user_id
         LEFT JOIN bank_user buu ON buu.user_id = b.acc_user_id;

alter table new_view
    owner to postgres;

create function update_balance_function() returns trigger
    language plpgsql
as
$$
BEGIN
    update account
    set acc_balance= acc_balance - NEW.txn_amount
    where account.acc_number =NEW.txn_account_from;
    update account
    set acc_balance= acc_balance + NEW.txn_amount
    where account.acc_number = NEW.txn_account_to;
    return null;
END

$$;

alter function update_balance_function() owner to postgres;

create trigger update_balance_trg
    after insert
    on transaction
    for each row
execute procedure update_balance_function();
insert into public.currency (cur_id, cur_name, cur_exchange_rate)
values  (643, 'RUB', 1),
        (840, 'USD', 89),
        (978, 'EUR', 96);

insert into public.bank_user (user_id, user_pass, user_login)
values  (2, '3', 'wetrist'),
        (1, '2', 'w3st125'),
        (3, '_', 'w3st227');


insert into public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
values  (2, 100.00, 5, '555', 840),
        (1, 100.00, 4, '444', 840),
        (1, 75.00, 1, '111', 643),
        (2, 87.00, 2, '222', 643),
        (3, 373.00, 3, '333', 643);

insert into public.transaction_type (txnt_id, txnt_name)
values  (1, 'PAY_IN'),
        (2, 'PAY_OUT'),
        (3, 'P2P');
