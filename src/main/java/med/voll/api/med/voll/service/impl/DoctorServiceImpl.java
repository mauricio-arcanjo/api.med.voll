package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.service.interfaces.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    //It's needed to add a bean configuration before use
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DoctorDto register(DoctorDto doctorDto) {

        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

        return modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);

    }
}