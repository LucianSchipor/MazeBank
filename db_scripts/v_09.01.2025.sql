create table bank_info
(
    info_id         int auto_increment
        primary key,
    current_intrest float not null
);

create table users
(
    username                varchar(20)  not null,
    password                varchar(20)  not null,
    role                    int          null comment 'User''s role (1 - admin, 2 - normal user)',
    user_id                 int auto_increment
        primary key,
    `2FA_Key`               varchar(150) not null,
    email                   varchar(30)  not null,
    `2FA_Verification_Time` datetime     null
);

create table bank_accounts
(
    account_id       char(16)    not null
        primary key,
    account_balance  float       not null,
    account_currency varchar(5)  not null,
    user_id          int         not null,
    pin              varchar(4)  not null,
    iban             varchar(20) not null,
    cvv              varchar(3)  not null,
    expire_date      date        not null,
    constraint bank_accounts_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table credits
(
    credit_id           int auto_increment
        primary key,
    credit_total_sum    float       not null,
    credit_period       int         not null,
    credit_currency     varchar(10) not null,
    credit_monthly_rate float       not null,
    credit_interest     float       not null,
    user_id             int         not null,
    constraint credits_credits__fk
        foreign key (user_id) references users (user_id)
);

create table forms
(
    form_id   int auto_increment
        primary key,
    form_path varchar(200) not null,
    status    int          not null comment '0-pending, 1-accepted, 2-rejected',
    user_id   int          not null,
    date      date         not null,
    form_type int          not null,
    constraint forms_forms__fk
        foreign key (user_id) references users (user_id)
);

create table transactions
(
    transaction_id int auto_increment
        primary key,
    sender         varchar(20) null,
    receiver       varchar(20) not null,
    message        varchar(50) null,
    currency       varchar(5)  null,
    amount         double      null,
    datetime       datetime    not null,
    constraint transactions_bank_accounts_account_id_fk
        foreign key (sender) references bank_accounts (account_id),
    constraint transactions_bank_accounts_account_id_fk_2
        foreign key (receiver) references bank_accounts (account_id)
);


