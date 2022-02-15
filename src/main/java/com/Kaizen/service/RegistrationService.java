package com.Kaizen.service;

import com.Kaizen.model.RegistrationModel;
import com.Kaizen.model.UserModel;
import com.Kaizen.model.UserRole;
import com.Kaizen.validation.EmailValidation;
import com.Kaizen.validation.token.ConfirmationToken;
import com.Kaizen.validation.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidation emailValidation;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationToken confirmationToken;

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
