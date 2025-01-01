package med.voll.api.med.voll.controller;

import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    @PostMapping
    public void register(@RequestBody @Valid DoctorDto doctorDto){

        doctorService.register(doctorDto);

    }

}
