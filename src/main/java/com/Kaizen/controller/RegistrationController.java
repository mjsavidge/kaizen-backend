package com.Kaizen.controller;

import lombok.AllArgsConstructor;
import com.Kaizen.model.RegistrationModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Kaizen.service.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationModel request){
        return registrationService.register(request);
    }
}
