package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.AppointmentCancelDto;
import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody @Valid AppointmentDto appointmentDto){

        return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto));

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
