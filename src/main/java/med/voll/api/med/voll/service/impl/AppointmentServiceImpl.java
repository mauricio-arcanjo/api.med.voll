package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppointmentDto createAppointment(LocalDateTime localDateTime) {
        return null;
    }
}
