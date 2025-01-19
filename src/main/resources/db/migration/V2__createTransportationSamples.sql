create table transportation
(
    ID                      BIGINT auto_increment primary key,
    TYPE                    ENUM('FLIGHT', 'BUS', 'SUBWAY', 'UBER'),
    OPERATING_DAYS          INTEGER ARRAY,
    ORIGIN_LOCATION_ID      BIGINT,
    DESTINATION_LOCATION_ID BIGINT,
    FOREIGN KEY (ORIGIN_LOCATION_ID) REFERENCES LOCATION (ID),
    FOREIGN KEY (DESTINATION_LOCATION_ID) REFERENCES LOCATION (ID)
);

insert into transportation (TYPE, OPERATING_DAYS, ORIGIN_LOCATION_ID, DESTINATION_LOCATION_ID)
values ('BUS', ARRAY[1, 2, 3, 4, 5, 6, 7], 4, 2),
       ('FLIGHT', ARRAY[1, 2, 3, 4, 5, 6, 7], 2, 3),
       ('UBER', ARRAY[1, 2, 3, 4, 5, 6, 7], 3, 6),
       ('SUBWAY', ARRAY[1, 2, 3, 4, 5, 6, 7], 4, 2);
