CREATE TABLE patients(

    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(12) NOT NULL,
    cpf VARCHAR(6) NOT NULL UNIQUE,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(20),
    complement VARCHAR(100),
    neighborhood VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    postalCode VARCHAR(9) NOT NULL,

    PRIMARY KEY (id)

);