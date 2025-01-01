package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.PatientDto;
import med.voll.api.med.voll.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientServiceImpl patientService;

    //Register Rest API
    @PostMapping
    @Transactional
    /*
        In APIs that involve critical operations, you can use @Transactional to ensure that errors don't leave the bank in an
        inconsistent state. If several actions need to be treated as a single unit of work (either all are completed, or none
        are applied). If an exception occurs, a rollback is automatically performed
     */
    public PatientDto register(@RequestBody @Valid PatientDto patientDto){

        return patientService.register(patientDto);

    }

}
