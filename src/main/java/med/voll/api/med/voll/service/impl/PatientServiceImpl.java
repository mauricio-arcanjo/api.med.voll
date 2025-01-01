package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.PatientDto;
import med.voll.api.med.voll.dto.PatientListDto;
import med.voll.api.med.voll.dto.PatientUpdateDto;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PatientDto register(PatientDto patientDto) {

        Patient patient = modelMapper.map(patientDto, Patient.class);

        return modelMapper.map(patientRepository.save(patient), PatientDto.class);
    }

    @Override
    public Page<PatientListDto> listPageable(Pageable pageable) {
        return patientRepository.findAll(pageable).map(PatientListDto::new);
    }

    @Override
    public PatientDto update(PatientUpdateDto patientUpdateDto) {

        var patient = patientRepository.getReferenceById(patientUpdateDto.id());

        patient.updateData(patientUpdateDto);

        return modelMapper.map(patientRepository.save(patient), PatientDto.class);
    }
}

