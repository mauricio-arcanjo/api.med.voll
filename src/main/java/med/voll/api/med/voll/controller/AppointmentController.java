package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.AppointmentCancelDto;
import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Appointment;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ModelMapper modelMapper;

    public AppointmentController(AppointmentService appointmentService, ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody @Valid AppointmentDto appointmentDto, UriComponentsBuilder uriComponentsBuilder){

        Appointment appointment = appointmentService.createAppointment(appointmentDto);
        URI uri = uriComponentsBuilder.path("/appointments/{id}")
                .buildAndExpand(appointment.getId()).toUri();

        return ResponseEntity.created(uri).body(modelMapper.map(appointment, AppointmentDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @GetMapping
    @RequestMapping("/patient/{id}")
    public ResponseEntity<List<AppointmentDto>> listAppointmentsByPatient (@PathVariable Long id){

        return ResponseEntity.ok(appointmentService.listByPatient(id));
    }

    @GetMapping
    @RequestMapping("/doctor/{id}")
    public ResponseEntity<List<AppointmentDto>> listAppointmentsByDoctor (@PathVariable Long id){

        return ResponseEntity.ok(appointmentService.listByDoctor(id));
    }

    @PutMapping
    @Transactional
    @RequestMapping("/cancel")
    public ResponseEntity<AppointmentDto> cancel(@RequestBody @Valid AppointmentCancelDto appointmentCancelDto){

        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentCancelDto));
    }

}
