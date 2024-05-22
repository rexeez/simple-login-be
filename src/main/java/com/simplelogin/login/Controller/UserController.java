package com.simplelogin.login.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.simplelogin.login.DTO.UserDetailsDto;
import com.simplelogin.login.DTO.UserLoginDto;
import com.simplelogin.login.DTO.UserRegisterDto;
import com.simplelogin.login.Entity.User;
import com.simplelogin.login.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired(required=true)
    private HttpServletRequest request;
    
    @PostMapping("/login")
    public ResponseEntity<UserDetailsDto> login(@RequestBody UserLoginDto userLoginDto){
        logger.info(userLoginDto.getUsername()+" "+userLoginDto.getPassword());
        Optional<User> userOptional = userRepository.findByUsername(userLoginDto.getUsername());
        logger.info("optional works");
        if (userOptional.isPresent()){
            User user = userOptional.get();
            logger.info("optional exists");
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setUsername(user.getUsername());
            logger.info("user got");
            HttpSession session = request.getSession();
            logger.info("session");
            session.setAttribute("user", user);
            logger.info("set user works");
            return new ResponseEntity<UserDetailsDto>(userDetailsDto, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username doesn't exists");
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> register(@RequestBody UserRegisterDto userRegisterDto){
        logger.info(userRegisterDto.getUsername()+" "+userRegisterDto.getPassword());
        Optional<User> findUser = userRepository.findByUsername(userRegisterDto.getUsername());
        if (findUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(userRegisterDto.getPassword());
        userRepository.save(user);
        return new ResponseEntity<UserRegisterDto>(userRegisterDto, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<User> logout(HttpSession session){
        try{
            User user = (User)session.getAttribute("user");
            session.invalidate();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
