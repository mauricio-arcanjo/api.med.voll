package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.model.dto.DoctorDto;
import med.voll.api.med.voll.model.dto.DoctorListDto;
import med.voll.api.med.voll.model.dto.DoctorUpdateDto;
import med.voll.api.med.voll.model.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {

    public Doctor register(DoctorDto doctorDto);
    public DoctorDto getById(Long id);
    public List<DoctorListDto> list();
    public Page<DoctorListDto> listPageable(Pageable pageable);
    public DoctorDto update(DoctorUpdateDto doctorUpdateDto);
    public void delete(Long id);
}
