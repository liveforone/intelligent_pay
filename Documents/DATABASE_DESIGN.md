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
    balance bigint,
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
    bankbook_num varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);
CREATE INDEX created_year_idx ON record (created_year);
CREATE INDEX created_year_month_idx ON record (created_year, created_month);
CREATE INDEX title_idx ON record (title);
CREATE INDEX record_state_idx ON record (record_state);
CREATE INDEX bankbook_num_idx ON record (bankbook_num);
CREATE INDEX username_idx ON record (username);
```

## DB 설계 원칙
* **역할**과 **책임**에 따라 DB를 분리하라.
* DB에 최소한의 필요한 데이터만 둔다.
* DB를 쪼개고 나누게되면 시스템이 유연하게 동작하기 때문에 두려워하지 말아라.
* 일례로 상품이 있다면 상품의 가격을 담당하는 부분과 별점과 리뷰를 담당하는 부분 등 여려가지 부분이 있다.
* 이것을 상품 DB에 모두 저장하고, 읽기 쓰기 작업을하게되면 부하가 한곳에 집중된다.
* 읽기를 할때 모든 데이터가 필요한것도 아니고, 쓰기를 할때도 마찬가지이다.
* 따라서 이를 나누어서 분산시켜, 필요할때 필요한 데이터만 리턴하도록 틀을 만든다.
* 쿠팡이 대표적인 예시로 제품의 이미지와 이름은 catalog팀에서, 
* 가격은 pricing 팀에서 재고 정보는 fulfillment 팀에서 제공하는 방식으로 하여 유연성을 높였다.
* 이러한 설계는향후 거대한 시스템과 대규모 트래픽 대응, 대용량 데이터처리에 훨씬 용이해질 것이다.