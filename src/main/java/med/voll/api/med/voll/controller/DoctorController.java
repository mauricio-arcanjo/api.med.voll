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

    //Register Rest API
    @PostMapping
    public DoctorDto register(@RequestBody @Valid DoctorDto doctorDto){

        return doctorService.register(doctorDto);
    }



}
