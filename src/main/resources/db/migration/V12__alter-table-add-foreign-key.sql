ALTER TABLE appointments
ADD CONSTRAINT fk_doctor_id
FOREIGN KEY (doctor_id)
REFERENCES doctors(id);

ALTER TABLE appointments
ADD CONSTRAINT fk_patient_id
FOREIGN KEY (patient_id)
REFERENCES patients(id);