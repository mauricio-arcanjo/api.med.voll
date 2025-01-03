package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public AppointmentDto createAppointment(@RequestBody @Valid AppointmentDto appointmentDto){

        return appointmentService.createAppointment(appointmentDto);

    }

    @GetMapping
    @RequestMapping("/patient/{id}")
    public List<AppointmentDto> listAppointmentsByPatient (@PathVariable Long id){

        return appointmentService.listByPatient(id);
    }

    @GetMapping
    @RequestMapping("/doctor/{id}")
    public List<AppointmentDto> listAppointmentsByDoctor (@PathVariable Long id){

        return appointmentService.listByDoctor(id);
    }

}
