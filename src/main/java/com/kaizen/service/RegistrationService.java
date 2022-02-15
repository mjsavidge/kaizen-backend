package com.kaizen.service;

import com.kaizen.model.RegistrationModel;
import com.kaizen.model.UserModel;
import com.kaizen.model.UserRole;
import com.kaizen.validation.EmailValidation;
import com.kaizen.validation.token.ConfirmationToken;
import com.kaizen.validation.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final EmailValidation emailValidation;
    @Autowired
    private final ConfirmationTokenService confirmationTokenService;


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

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUserModel().getEmail());
        return "confirmed";
    }
}
