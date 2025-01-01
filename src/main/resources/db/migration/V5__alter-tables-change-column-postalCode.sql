ALTER TABLE patients
CHANGE COLUMN postalCode postal_code VARCHAR(9) NOT NULL;

ALTER TABLE doctors
CHANGE COLUMN postalCode postal_code VARCHAR(9) NOT NULL;
