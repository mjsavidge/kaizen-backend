package com.kaizen.controller;

import com.kaizen.model.LogInModel;
import lombok.AllArgsConstructor;
import com.kaizen.model.RegistrationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.kaizen.service.RegistrationService;

@CrossOrigin(origins = "localhost://4200")
@RestController
@RequestMapping(path = "api/v1/")
@AllArgsConstructor
public class RegistrationController {


    private final RegistrationService registrationService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @PostMapping("registration")
    public String register(@RequestBody RegistrationModel request){
        return registrationService.register(request);
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

    @PostMapping("signin")
    public ResponseEntity<String> authUser(@RequestBody LogInModel logInModel){
        Authentication auth = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                logInModel.getUsernameOrEmail(), logInModel.getPassword()));

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
