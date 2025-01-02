CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    initial_time_day DATETIME NOT NULL,
    ending_time_day DATETIME NOT NULL
);