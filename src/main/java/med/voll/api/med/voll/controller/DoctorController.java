package med.voll.api.med.voll.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody String json){
        System.out.println(json);

    }

}
