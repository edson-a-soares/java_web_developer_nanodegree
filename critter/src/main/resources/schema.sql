CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    notes TEXT
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    skill_set TEXT NOT NULL,
    availability VARCHAR(40) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    pet_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    type VARCHAR(15) NOT NULL,
    birth_date DATE NOT NULL,
    notes TEXT,
    owner_id BIGINT,
    FOREIGN KEY (owner_id)  REFERENCES customers (customer_id)
);
