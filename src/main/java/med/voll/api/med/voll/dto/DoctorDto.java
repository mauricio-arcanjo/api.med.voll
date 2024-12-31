package med.voll.api.med.voll.dto;

import med.voll.api.med.voll.model.Speciality;

public record DoctorDto(
        String name,
        String email,
        String phone,
        String crm,
        Speciality speciality,
        AddressDto address
) {
}
