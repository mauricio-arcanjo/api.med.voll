package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.dto.DoctorListDto;
import med.voll.api.med.voll.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    //Register Rest API
    @PostMapping
    @Transactional
    /*
        In APIs that involve critical operations, you can use @Transactional to ensure that errors don't leave the bank in an
        inconsistent state. If several actions need to be treated as a single unit of work (either all are completed, or none
        are applied). If an exception occurs, a rollback is automatically performed
     */
    public DoctorDto register(@RequestBody @Valid DoctorDto doctorDto){

        return doctorService.register(doctorDto);
    }

    @GetMapping
    public List<DoctorListDto> list(){
        return doctorService.list();
    }



}
