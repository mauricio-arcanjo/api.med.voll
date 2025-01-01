package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.dto.DoctorListDto;
import med.voll.api.med.voll.dto.DoctorUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {

    public DoctorDto register(DoctorDto doctorDto);
    public List<DoctorListDto> list();
    public Page<DoctorListDto> listPageable(Pageable pageable);
    public DoctorDto update(DoctorUpdateDto doctorUpdateDto);
}
