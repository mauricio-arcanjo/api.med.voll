package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.model.dto.DoctorDto;
import med.voll.api.med.voll.model.dto.DoctorListDto;
import med.voll.api.med.voll.model.dto.DoctorUpdateDto;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.service.interfaces.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    //It's needed to add a bean configuration before use
    private final ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Doctor register(DoctorDto doctorDto) {

        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorDto getById(Long id) {

        return modelMapper
                .map(doctorRepository.getReferenceById(id), DoctorDto.class);
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
    public void delete(Long id) {

        var doctor = doctorRepository.getReferenceById(id);
        doctor.setActive(false);
        modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);
    }


}
