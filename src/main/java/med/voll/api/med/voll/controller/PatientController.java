package med.voll.api.med.voll.controller;

import med.voll.api.med.voll.dto.PatientDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @PostMapping
    public void register(@RequestBody PatientDto patientDto){
        System.out.println(patientDto);

    }

}
