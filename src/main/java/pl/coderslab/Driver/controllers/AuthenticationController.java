package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.AdviceConverter;
import pl.coderslab.Driver.converters.DisplayConverter;
import pl.coderslab.Driver.converters.UserConverter;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.dto.UserDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @PostMapping("/registerUser")
    public void registerUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertUserDtoToUser(userDto);
        userRepository.save(user);
    }

}
