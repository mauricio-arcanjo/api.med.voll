package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.dto.DoctorListDto;
import med.voll.api.med.voll.dto.DoctorUpdateDto;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.service.interfaces.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<DoctorListDto> list() {

        return doctorRepository.findAll().stream().map(DoctorListDto::new).toList();
    }

    @Override
    public Page<DoctorListDto> listPageable(Pageable pageable) {

        return doctorRepository.findAllByActiveTrue(pageable).map(DoctorListDto::new);
    }

    @Override
    public DoctorDto update(DoctorUpdateDto doctorUpdateDto) {

        var doctor = doctorRepository.getReferenceById(doctorUpdateDto.id());

        doctor.updateData(doctorUpdateDto);

        return modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);
    }

    @Override
    public DoctorDto delete(Long id) {

        var doctor = doctorRepository.getReferenceById(id);
        doctor.setActive(false);

        return modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);
    }


}
