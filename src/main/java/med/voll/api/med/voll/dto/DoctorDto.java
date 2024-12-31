package med.voll.api.med.voll.dto;

import med.voll.api.med.voll.model.Speciality;

public record DoctorDto(
        String nome,
        String email,
        String phone,
        String crm,
        Speciality speciality,
        AddressDto address
) {
}
