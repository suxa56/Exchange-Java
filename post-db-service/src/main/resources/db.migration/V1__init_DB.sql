create table currency_rate
(
    id          bigint not null,
    ccy         varchar(255),
    rate        double precision,
    actual_date date
    );