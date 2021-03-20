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
    availability VARCHAR(100) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS pets (
    pet_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    type VARCHAR(15) NOT NULL,
    birth_date DATE NOT NULL,
    notes TEXT,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id)  REFERENCES customers (customer_id)
);

CREATE TABLE IF NOT EXISTS schedules (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    delivery_date DATE NOT NULL,
    activities TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS schedules_pets (
    schedule_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    FOREIGN KEY (pet_id)  REFERENCES pets (pet_id),
    FOREIGN KEY (schedule_id)  REFERENCES schedules (schedule_id)
);

CREATE TABLE IF NOT EXISTS schedules_employees (
    schedule_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    FOREIGN KEY (employee_id)  REFERENCES employees (employee_id),
    FOREIGN KEY (schedule_id)  REFERENCES schedules (schedule_id)
);
