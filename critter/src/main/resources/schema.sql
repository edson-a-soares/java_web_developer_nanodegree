CREATE TABLE IF NOT EXISTS customers (
    customer_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    notes varchar(255)
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees_skills (
    skill_name VARCHAR(50) NOT NULL,
    employee_id BIGINT NOT NULL,
    FOREIGN KEY (employee_id)  REFERENCES employees (employee_id)
);

CREATE TABLE IF NOT EXISTS employees_availability (
    weekday VARCHAR(40) NOT NULL,
    employee_id BIGINT NOT NULL,
    FOREIGN KEY (employee_id)  REFERENCES employees (employee_id)
);

CREATE TABLE IF NOT EXISTS pets (
    pet_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    type VARCHAR(15) NOT NULL,
    birth_date DATE,
    notes varchar(255),
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id)  REFERENCES customers (customer_id)
);

CREATE TABLE IF NOT EXISTS schedules (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    delivery_date DATE NOT NULL,
    activities varchar(255) NOT NULL
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
