package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.*;
import med.voll.api.med.voll.model.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    public Patient register(PatientDto patientDto);
    public PatientDto getById(Long id);
    public Page<PatientListDto> listPageable(Pageable pageable);
    public PatientDto update(PatientUpdateDto patientUpdateDto);
    public PatientDto delete(Long id);
}
