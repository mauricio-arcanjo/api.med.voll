package med.voll.api.med.voll.controller;

import jakarta.validation.Valid;
import jakarta.transaction.Transactional;
import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.model.Doctor;
import med.voll.api.med.voll.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    @Transactional
    /*
        In APIs that involve critical operations, you can use @Transactional to ensure that errors don't leave the bank in an
        inconsistent state. If several actions need to be treated as a single unit of work (either all are completed, or none
        are applied). If an exception occurs, a rollback is automatically performed
     */
    public void register(@RequestBody @Valid DoctorDto doctorDto){

        doctorRepository.save(new Doctor(doctorDto));

    }

}
