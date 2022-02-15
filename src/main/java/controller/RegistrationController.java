package controller;

import lombok.AllArgsConstructor;
import model.RegistrationModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    public String register(@RequestBody RegistrationModel request){
        return registrationService.register(request);
    }
}
