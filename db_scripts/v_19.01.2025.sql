create table bank_info
(
    info_id         int auto_increment
        primary key,
    current_intrest varchar(150) not null
);

create table transactions
(
    transaction_id int auto_increment
        primary key,
    sender         varchar(150) not null,
    receiver       varchar(150) not null,
    message        varchar(50)  null,
    currency       varchar(150) not null,
    amount         varchar(150) not null,
    datetime       datetime     not null
);

create index transactions_transactions__fk
    on transactions (sender);

create index transactions_transactions__fk_2
    on transactions (receiver);

create table users
(
    username                varchar(150) not null,
    password                varchar(150) not null,
    role                    int          null comment 'User''s role (1 - admin, 2 - normal user)',
    user_id                 int auto_increment
        primary key,
    `2FA_Key`               varchar(150) not null,
    email                   varchar(150) not null,
    `2FA_Verification_Time` datetime     null
);

create table bank_accounts
(
    account_id       char(150)    not null,
    account_balance  varchar(150) not null,
    account_currency varchar(150) not null,
    user_id          int          not null,
    pin              varchar(150) not null,
    iban             varchar(150) not null
        primary key,
    cvv              varchar(150) not null,
    expire_date      date         not null,
    constraint bank_accounts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table credits
(
    credit_id           int auto_increment
        primary key,
    credit_total_sum    varchar(150) not null,
    credit_period       varchar(150) not null,
    credit_currency     varchar(150) not null,
    credit_monthly_rate varchar(150) not null,
    credit_interest     varchar(150) not null,
    user_id             int          not null,
    constraint credits_credits__fk
        foreign key (user_id) references users (user_id)
);

create table forms
(
    form_id   int auto_increment
        primary key,
    form_path varchar(200) not null,
    status    varchar(150) not null comment '0-pending, 1-accepted, 2-rejected',
    user_id   int          not null,
    date      date         not null,
    form_type varchar(150) not null,
    constraint forms_forms__fk
        foreign key (user_id) references users (user_id)
);


