package service;

import model.RegistrationModel;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(RegistrationModel request){
        return "works";
    }
}
