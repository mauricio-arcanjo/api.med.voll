ALTER TABLE patients
CHANGE COLUMN cpf cpf VARCHAR(14) NOT NULL UNIQUE;