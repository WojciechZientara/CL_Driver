package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.UserDto;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.jwt.JwtTokenUtil;
import pl.coderslab.Driver.repositories.RoleRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.util.HashSet;

@Component
public class UserConverter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    
    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setTestResults(new HashSet<>());
        user.setMessages(new HashSet<>());
        user.setDisplays(new HashSet<>());
        user.setAdvices(new HashSet<>());
        user.setRoles(new HashSet<>());
        user.getRoles().add(roleRepository.findRoleByName("ROLE_USER"));
        return user;
    }

    public User convertTokenToUser(String token) {
        String userEmail = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return userRepository.findUserByEmail(userEmail);
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public User updateUser(User user, User update) {
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setEmail(update.getEmail());
        user.setPassword(passwordEncoder.encode(update.getPassword()));
        return user;
    }

}
