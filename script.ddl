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

create table if not exists COUNTERPARTY
(
    ID BIGINT auto_increment not null
        primary key,
    INN bigint,
    OGRN bigint,
    unique (INN), unique (OGRN)
);

create table if not exists COUNTERPARTY_ACC
(
    ID BIGINT auto_increment not null primary key,
    NUM varchar(20),
    BALANCE double,
    ID_COUNTERPARTY BIGINT references COUNTERPARTY (ID) on delete cascade,
    unique (NUM)
);

create table if not exists CLIENT_COUNTERPARTY
(
    ID BIGINT auto_increment not null primary key,
    ID_CLIENT bigint references CLIENTS (id) on delete cascade,
    ID_COUNTERPARTY bigint references COUNTERPARTY (id) on delete cascade
);

create index passport_index on CLIENTS (PASSPORT_ID);
create index acc_index on BANK_ACCOUNT (NUM);
create index num_index on CARDS (NUM);