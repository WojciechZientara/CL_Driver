package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.UserConverter;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.entities.Role;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.repositories.RoleRepository;
import pl.coderslab.driver.repositories.UserRepository;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){

        User user = userRepository.findUserById(userId);
        if (user == null) {
            UserDto empty = new UserDto();
            empty.setEmail("No user with such id");
            return ResponseEntity.ok(empty);
        } else {
            return ResponseEntity.ok(userConverter.convertUserToUserDto(user));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        Role role = roleRepository.findRoleByName("ROLE_USER");
        User user = userConverter.convertUserDtoToUser(userDto, role);
        userDto.setId(userRepository.save(user).getId());
        userDto.setPassword(null);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/")
    public ResponseEntity<?> editUser(@RequestBody UserDto userDto) {
        Role role = roleRepository.findRoleByName("ROLE_USER");
        User user = userRepository.findUserById(userDto.getId());
        User update = userConverter.convertUserDtoToUser(userDto, role);
        user = userConverter.applyChanges(user, update);
        userRepository.save(user);
        userDto = userConverter.convertUserToUserDto(user);
        userDto.setPassword(null);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userRepository.clearUserRoleAssociations(userId);
        userRepository.deleteById(userId);
        return ResponseEntity.ok().body("Success");
    }

}
