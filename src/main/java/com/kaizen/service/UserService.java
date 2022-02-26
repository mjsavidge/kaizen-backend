package com.kaizen.service;

import com.kaizen.model.UserModel;
import com.kaizen.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.kaizen.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "user with email %s was not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserModel userModel =  userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(()
                -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, usernameOrEmail)));
        return new org.springframework.security.core.userdetails.User(
                userModel.getEmail(),
                userModel.getPassword(),
                userModel.getAuthorities()
        );
    }

    public String signUpUser(UserModel userModel){
        boolean userExists = userRepository.findByEmail(userModel.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(userModel.getPassword());

        userModel.setPassword(encodedPassword);

        userRepository.save(userModel);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), userModel
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    @SuppressWarnings("UnusedReturnValue")
    public int enableUser(String email){
        return userRepository.enableUser(email);
    }
}
