package med.voll.api.med.voll.dto;

import med.voll.api.med.voll.model.entity.Patient;

public record PatientListDto(

        String name,
        String email,
        String cpf

) {

    public PatientListDto(Patient patient){
        this(patient.getName(), patient.getEmail(), patient.getCpf());
    }

}
