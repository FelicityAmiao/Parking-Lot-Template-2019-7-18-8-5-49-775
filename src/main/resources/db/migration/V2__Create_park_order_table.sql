create table park_order(
    id long primary key auto_increment,
    parking_lot_name varchar(255),
    car_id varchar(255),
    create_time Timestamp,
    end_time Timestamp,
    order_status Boolean,
    fk_1 varchar(255)
);