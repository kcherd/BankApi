create table if not exists CLIENTS
(
    ID BIGINT auto_increment not null
        primary key,
    FIO VARCHAR(100) not null,
    PASSPORT_ID VARCHAR(10) not null,
    unique (PASSPORT_ID)
);

create table if not exists BANK_ACCOUNT
(
    ID BIGINT auto_increment not null
        primary key,
    NUM VARCHAR(20) not null,
    BALANCE DOUBLE,
    ID_USER INTEGER
        references CLIENTS,
    unique (NUM)
);

create table if not exists CARDS
(
    ID BIGINT auto_increment not null
        primary key,
    NUM BIGINT not null,
    ACC_ID INTEGER
        references BANK_ACCOUNT,
    unique (NUM)
);