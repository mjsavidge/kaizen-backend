package com.Kaizen.service;

import com.Kaizen.model.RegistrationModel;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public String register(RegistrationModel request){
        return "works";
    }
}
