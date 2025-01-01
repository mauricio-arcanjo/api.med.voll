package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.model.Doctor;
import med.voll.api.med.voll.repository.DoctorRepository;
import med.voll.api.med.voll.service.DoctorService;
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

        System.out.println("Received Doctor DTO: " + doctorDto);
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        System.out.println("Mapped Doctor: " + doctor);
//        doctorRepository.save(doctor);
        return modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);

    }
}