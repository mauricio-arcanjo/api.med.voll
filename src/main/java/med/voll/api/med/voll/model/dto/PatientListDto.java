package med.voll.api.med.voll.model.dto;

import med.voll.api.med.voll.model.entity.Patient;

public record PatientListDto(

        Long id,
        String name,
        String email,
        String cpf

) {

    public PatientListDto(Patient patient){

        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }

}
