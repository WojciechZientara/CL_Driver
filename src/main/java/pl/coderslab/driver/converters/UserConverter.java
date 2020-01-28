package pl.coderslab.driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.entities.Role;
import pl.coderslab.driver.entities.User;
import java.util.HashSet;

@Component
public class UserConverter {

    @Autowired
    PasswordEncoder passwordEncoder;
    
    public User convertUserDtoToUser(UserDto userDto, Role role) {
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
        user.getRoles().add(role);
        return user;
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

    public User applyChanges(User user, User update) {
        user.setFirstName(update.getFirstName());
        user.setLastName(update.getLastName());
        user.setEmail(update.getEmail());
        user.setPassword(passwordEncoder.encode(update.getPassword()));
        return user;
    }

}
