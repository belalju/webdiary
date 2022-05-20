package com.bd.webdiary.api;

import com.bd.webdiary.jwt.JwtProvider;
import com.bd.webdiary.jwt.JwtResponse;
import com.bd.webdiary.model.LoginInfo;
import com.bd.webdiary.model.ResponseMessage;
import com.bd.webdiary.model.User;
import com.bd.webdiary.service.UserPrinciple;
import com.bd.webdiary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserApi {
    private final UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    public UserApi(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity create(@Valid @RequestBody User user) {
        return ResponseEntity.ok(this.userService.save(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(this.userService.getUserList());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginInfo loginInfo) {

        String jwt = "";
        UserDetails userDetails = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginInfo.getUserName(), loginInfo.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtProvider.generateJwtToken(authentication);
            userDetails = (UserDetails) authentication.getPrincipal();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {

        }

        if(userDetails != null){
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
        }else{
            return new ResponseEntity<>(new ResponseMessage("Login Failed"), HttpStatus.UNAUTHORIZED);
        }

    }
}
