package med.voll.api.med.voll.dto;

import med.voll.api.med.voll.model.Speciality;

public record PatientDto(
        String name,
        String email,
        String phone,
        String cpf,
        AddressDto address
) {
}
