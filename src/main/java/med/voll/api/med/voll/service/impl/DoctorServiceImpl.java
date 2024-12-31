package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.model.Doctor;
import med.voll.api.med.voll.repository.DoctorRepository;
import med.voll.api.med.voll.service.DoctorService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

//    private final ModelMapper modelMapper;

//    public DoctorServiceImpl(ModelMapper modelMapper, DoctorRepository doctorRepository) {
////        this.modelMapper = modelMapper;
//        this.doctorRepository = doctorRepository;
//    }

    @Override
    public DoctorDto register(DoctorDto doctorDto) {

//        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
        System.out.println(doctorDto);
        return null;

    }
}