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

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Patient register(PatientDto patientDto) {

        return patientRepository
                .save(modelMapper.map(patientDto, Patient.class));
    }

    @Override
    public PatientDto getById(Long id) {

        return modelMapper
                .map(patientRepository.getReferenceById(id), PatientDto.class);

    }

    @Override
    public Page<PatientListDto> listPageable(Pageable pageable) {
        return patientRepository
                .findAllByActiveTrue(pageable).map(PatientListDto::new);
    }

    @Override
    public PatientDto update(PatientUpdateDto patientUpdateDto) {

        var patient = patientRepository.getReferenceById(patientUpdateDto.id());

        patient.updateData(patientUpdateDto);

        return modelMapper
                .map(patientRepository.save(patient), PatientDto.class);
    }

    @Override
    public PatientDto delete(Long id) {

        var patient = patientRepository.getReferenceById(id);
        patient.setActive(false);

        return modelMapper
                .map(patientRepository.save(patient), PatientDto.class);

    }
}

