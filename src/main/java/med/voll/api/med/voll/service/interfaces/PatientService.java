package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.PatientDto;
import med.voll.api.med.voll.dto.PatientListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {

    public PatientDto register(PatientDto patientDto);
    public Page<PatientListDto> listPageable(Pageable pageable);

}
