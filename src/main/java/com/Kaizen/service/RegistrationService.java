package com.Kaizen.service;

import com.Kaizen.model.RegistrationModel;
import com.Kaizen.model.UserModel;
import com.Kaizen.model.UserRole;
import com.Kaizen.validation.EmailValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidation emailValidation;

    public String register(RegistrationModel request){
        boolean isValidEmail = emailValidation.test(request.getEmail());
        if(!isValidEmail){
            throw  new IllegalStateException("Email not vaild");
        }
        return userService.signUpUser(
                new UserModel(
                        request.getName(),
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }
}
