package com.kaizen.controller;

import lombok.AllArgsConstructor;
import com.kaizen.model.RegistrationModel;
import org.springframework.web.bind.annotation.*;
import com.kaizen.service.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {


    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationModel request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
