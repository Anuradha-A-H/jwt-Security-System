package com.example.security_last_try.controller;


import com.example.security_last_try.dto.JwtAuthenticationResponse;
import com.example.security_last_try.dto.RefreshTokenRequest;
import com.example.security_last_try.dto.SignUpRequest;
import com.example.security_last_try.dto.SigninRequest;
import com.example.security_last_try.entity.User;
import com.example.security_last_try.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest request){
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){

        return ResponseEntity.ok(authenticationService.signin(signinRequest));


    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest signinRequest){
            System.out.println(signinRequest.getToken());
        return ResponseEntity.ok(authenticationService.refreshToken(signinRequest));
            //return null;

    }
}
