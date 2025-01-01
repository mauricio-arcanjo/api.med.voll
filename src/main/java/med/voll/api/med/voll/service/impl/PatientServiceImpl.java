package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.PatientDto;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
