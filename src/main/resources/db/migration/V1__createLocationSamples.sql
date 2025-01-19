create table location
(
    ID      BIGINT auto_increment primary key,
    CODE    CHARACTER VARYING(10),
    NAME    CHARACTER VARYING(255),
    COUNTRY CHARACTER VARYING(255),
    CITY    CHARACTER VARYING(255)
);

insert into location (CITY, CODE, COUNTRY, NAME)
values ('istanbul', 'SAW', 'türkiye', 'Sabiha Gökçen Airport'),
       ('istanbul', 'IST', 'türkiye', 'istanbul airport'),
       ('london', 'LHA', 'uk', 'London Heathrow Airport'),
       ('istanbul', 'TS', 'türkiye', 'Taksim Square'),
       ('istanbul', 'KP', 'türkiye', 'Kabataş Pier'),
       ('london', 'WS', 'uk', 'Wembley Stadium');