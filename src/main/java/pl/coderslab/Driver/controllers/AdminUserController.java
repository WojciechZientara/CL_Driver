package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.AdviceConverter;
import pl.coderslab.Driver.converters.UserConverter;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.dto.UserDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.UserRepository;

@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;


    @GetMapping("/getUser/{userId}")
    public ResponseEntity<?> getAdvice(@PathVariable Long userId){

        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResponseEntity.ok("No user with such id");
        } else {
            return ResponseEntity.ok(userConverter.convertUserToUserDto(user));
        }
    }

    @PostMapping("/postUser")
    public ResponseEntity<?> postUser(@RequestBody UserDto userDto) {
        User user = userConverter.convertUserDtoToUser(userDto);
        userDto.setId(userRepository.save(user).getId());
        userDto.setPassword(null);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDto) {
        User user = userRepository.findUserById(userDto.getId());
        User update = userConverter.convertUserDtoToUser(userDto);
        user = userConverter.updateUser(user, update);
        userRepository.save(user);
        userDto = userConverter.convertUserToUserDto(user);
        userDto.setPassword(null);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userRepository.clearUserRoleAssociations(userId);
        userRepository.deleteById(userId);
        return ResponseEntity.ok().body("Success");
    }


}
