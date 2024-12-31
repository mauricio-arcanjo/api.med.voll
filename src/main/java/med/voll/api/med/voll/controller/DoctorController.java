package med.voll.api.med.voll.controller;

import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.DoctorDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody @Valid DoctorDto doctorDto){
        System.out.println(doctorDto);

    }

}
