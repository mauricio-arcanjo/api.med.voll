package med.voll.api.med.voll.dto;

import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Speciality;

public record DoctorListDto (

    Long id,
    String name,
    String email,
    String crm,
    Speciality speciality

){

    public DoctorListDto(Doctor doctor) {
        this (doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpeciality());
    }
}
