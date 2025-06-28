DROP TYPE IF EXISTS booking_status;


CREATE TYPE booking_status AS ENUM ('BOOKED', 'CANCELLED', 'CHECKED_IN', 'WAITLISTED');

CREATE TABLE user_data (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    country VARCHAR(100),
    role role NOT NULL,
    verification_token VARCHAR(255),
    reset_token VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE package_data (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    credits INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    valid_days INT NOT NULL,
    is_available BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE class_schedule (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    required_credits INT NOT NULL,
    max_slots INT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    is_ended BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_package (
    id SERIAL PRIMARY KEY,
    user_data_id INT NOT NULL REFERENCES user_data(id),
    package_data_id INT NOT NULL REFERENCES package_data(id),
    remaining_credits INT NOT NULL,
    purchase_date TIMESTAMP NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

CREATE TABLE booking (
    id SERIAL PRIMARY KEY,
    user_data_id INT NOT NULL REFERENCES user_data(id),
    class_schedule_id INT NOT NULL REFERENCES class_schedule(id),
    user_package_id INT NOT NULL REFERENCES user_package(id),
    booking_time TIMESTAMP NOT NULL,
    status booking_status NOT NULL
);

CREATE TABLE waitlist (
    id SERIAL PRIMARY KEY,
    user_data_id INT NOT NULL REFERENCES user_data(id),
    class_schedule_id INT NOT NULL REFERENCES class_schedule(id),
    added_at TIMESTAMP NOT NULL,
    moved_to_booking BOOLEAN NOT NULL DEFAULT FALSE
);
