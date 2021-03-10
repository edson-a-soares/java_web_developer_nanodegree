CREATE TABLE IF NOT EXISTS prices (
    vehicleid BIGINT PRIMARY KEY AUTO_INCREMENT,
    currency VARCHAR(3),
    price NUMBER
);
