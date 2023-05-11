# DB 설계와 모델링

## DB 설계
```
CREATE DATABASE intelligent_pay_user;
CREATE DATABASE intelligent_pay_bankbook;
CREATE DATABASE intelligent_pay_record;
CREATE DATABASE intelligent_pay_statement;
CREATE DATABASE intelligent_pay_payment;
```

## DB별 테이블(인덱스 포함) 모델링
### User DB
```
CREATE TABLE member (
    id bigint not null auto_increment,
    auth varchar(255),
    email varchar(255) UNIQUE not null,
    password varchar(100) not null,
    real_name varchar(255) not null,
    username varchar(255) UNIQUE not null,
    primary key (id)
);
CREATE INDEX email_idx ON member (email);
CREATE INDEX username_idx ON member (username);
```
### Bankbook DB
```
CREATE TABLE bankbook (
    id bigint not null auto_increment,
    balance bigint not null,
    password varchar(100) not null
    bankbook_num varchar(255) UNIQUE not null,
    username varchar(255) UNIQUE not null,
    bankbook_state varchar(255),
    created_date date,
    primary key (id)
);
CREATE INDEX username_idx ON bankbook (username);
CREATE INDEX bankbook_num_idx ON bankbook (bankbook_num);
```
### Record DB
```
CREATE TABLE record (
    id bigint not null auto_increment,
    money bigint not null,
    created_year integer,
    created_month varchar(255) not null,
    record_state varchar(255),
    title varchar(255) not null,
    bankbook_num varchar(255) UNIQUE not null,
    username varchar(255) UNIQUE not null,
    created_date datetime(6),
    primary key (id)
);
CREATE INDEX created_year_idx ON record (created_year);
CREATE INDEX created_year_month_idx ON record (created_year, created_month);
CREATE INDEX title_idx ON record (title);
CREATE INDEX bankbook_num_idx ON record (bankbook_num);
CREATE INDEX username_idx ON record (username);
```