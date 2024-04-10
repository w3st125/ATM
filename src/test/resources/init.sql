create table transaction_type
(
    txnt_id   bigint      not null
        constraint txn_type_pk
            primary key,
    txnt_name varchar(32) not null
        constraint txn_type_unique
            unique
);

create table currency
(
    cur_id            bigint not null
        constraint currency_pk
            primary key,
    cur_name          varchar(16),
    cur_exchange_rate numeric(20, 2)
);

create table transaction
(
    txn_id               bigserial
        constraint transaction_pk
            primary key,
    txn_account_from     varchar(16),
    txn_account_to       varchar(16),
    txn_date             timestamp      not null,
    txn_type_id          bigint         not null
        constraint transaction_transaction_type_txnt_id_fk
            references transaction_type,
    txn_amount_from      numeric(20, 2) not null,
    txn_currency_id_from bigint,
    txn_currency_id_to   bigint
        constraint transaction_currency_id_to_fk
            references currency,
    txn_amount_to        numeric(20, 2)
);

create table currency_pair
(
    cer_pair_id          integer not null
        constraint currency_pair_pk
            primary key,
    cer_currency_id_from bigint
        constraint currency_pair_currency_cur_id_fk
            references currency,
    cer_currency_id_to   integer
        constraint currency_pair_currency_cur_id_fk2
            references currency,
    cer_exchange_rate    numeric(20, 4)
);

create table user_role
(
    role_id   integer not null
        constraint user_role_pk
            primary key,
    role_name varchar(16)
);

create table bank_user
(
    user_id      bigint      not null
        constraint users_pkey
            primary key,
    user_pass    varchar(32) not null,
    user_login   varchar(16) not null
        constraint users_user_login_key
            unique
        constraint user_login_must_be_lower_case
            check ((user_login)::text = lower((user_login)::text)),
    user_role_id integer
        constraint bank_user_user_role_role_id_fk
            references user_role
);

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

create view new_view(txn_id, txn_date, txn_amount, txnt_name, user_from, user_to) as
SELECT transaction.txn_id,
       transaction.txn_date,
       transaction.txn_amount_from AS txn_amount,
       tt.txnt_name,
       buu.user_login              AS user_from,
       bu.user_login               AS user_to
FROM transaction
         JOIN transaction_type tt ON transaction.txn_type_id = tt.txnt_id
         LEFT JOIN account a ON transaction.txn_account_to::text = a.acc_number::text
         LEFT JOIN account b ON transaction.txn_account_from::text = b.acc_number::text
         LEFT JOIN bank_user bu ON bu.user_id = a.acc_user_id
         LEFT JOIN bank_user buu ON buu.user_id = b.acc_user_id;

create function update_balance_function() returns trigger
    language plpgsql
as
$$
BEGIN
    update account
    set acc_balance= acc_balance - NEW.txn_amount_from
    where account.acc_number = NEW.txn_account_from;
    update account
    set acc_balance= acc_balance + NEW.txn_amount_to
    where account.acc_number = NEW.txn_account_to;
    return null;
END
$$;

create trigger update_balance_trg
    after insert
    on transaction
    for each row
execute procedure update_balance_function();

INSERT INTO public.transaction_type (txnt_id, txnt_name)
VALUES (1, 'PAY_IN');
INSERT INTO public.transaction_type (txnt_id, txnt_name)
VALUES (2, 'PAY_OUT');
INSERT INTO public.transaction_type (txnt_id, txnt_name)
VALUES (3, 'P2P');

INSERT INTO public.user_role (role_id, role_name)
VALUES (1, 'ADMIN');
INSERT INTO public.user_role (role_id, role_name)
VALUES (2, 'USER');

INSERT INTO public.currency (cur_id, cur_name, cur_exchange_rate)
VALUES (643, 'RUB', 1.00);
INSERT INTO public.currency (cur_id, cur_name, cur_exchange_rate)
VALUES (840, 'USD', 89.00);
INSERT INTO public.currency (cur_id, cur_name, cur_exchange_rate)
VALUES (978, 'EUR', 96.00);

INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (7, 840, 840, 1.0000);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (1, 840, 978, 0.9288);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (6, 840, 643, 91.2434);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (2, 978, 840, 1.0765);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (9, 978, 978, 1.0000);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (3, 978, 643, 98.2279);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (5, 643, 840, 0.0109);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (4, 643, 978, 0.0101);
INSERT INTO public.currency_pair (cer_pair_id, cer_currency_id_from, cer_currency_id_to, cer_exchange_rate)
VALUES (8, 643, 643, 1.0000);

INSERT INTO public.bank_user (user_id, user_pass, user_login, user_role_id)
VALUES (2, '3', 'wetrist', 2);
INSERT INTO public.bank_user (user_id, user_pass, user_login, user_role_id)
VALUES (3, '_', 'w3st227', 2);
INSERT INTO public.bank_user (user_id, user_pass, user_login, user_role_id)
VALUES (1, '2', 'w3st125', 1);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (1, 100.00, 4, '444', 840);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (2, 78.00, 5, '555', 840);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (1, 84.00, 1, '111', 643);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (2, 35.00, 2, '222', 643);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (3, 13.02, 6, '666', 840);
INSERT INTO public.account (acc_user_id, acc_balance, acc_id, acc_number, acc_currency_id)
VALUES (3, 106.82, 3, '333', 978);
