package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.dto.DoctorListDto;

import java.util.List;

public interface DoctorService {

    public DoctorDto register(DoctorDto doctorDto);
    public List<DoctorListDto> list();

}
