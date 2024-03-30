package com.example.security_last_try.service;


import com.example.security_last_try.dto.JwtAuthenticationResponse;
import com.example.security_last_try.dto.RefreshTokenRequest;
import com.example.security_last_try.dto.SignUpRequest;
import com.example.security_last_try.dto.SigninRequest;
import com.example.security_last_try.entity.Role;
import com.example.security_last_try.entity.User;
import com.example.security_last_try.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public User signUp(SignUpRequest signUpRequest)
    {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setSecondName(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        System.out.println(user.getPassword());
        userRepository.save(user);
        return user;
    }

    public JwtAuthenticationResponse signin(SigninRequest request)
    {
        System.out.println(request.getEmail()+" "+request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));


            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid Email Or Password."));
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return jwtAuthenticationResponse;

    }


    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request){


        String userEmail = jwtService.extractUserName(request.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if( jwtService.isTokenValid(request.getToken(),user))
        {   System.out.println(request.getToken());
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(request.getToken());
            return jwtAuthenticationResponse;

        }
        return null;


    }


}
