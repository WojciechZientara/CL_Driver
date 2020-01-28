package pl.coderslab.driver.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.converters.UserConverter;
import pl.coderslab.driver.dto.UserDto;
import pl.coderslab.driver.entities.Role;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.repositories.RoleRepository;
import pl.coderslab.driver.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                roles);
    }

    public User save(UserDto userDto) {
        Role role = roleRepository.findRoleByName("ROLE_USER");
        User user = userConverter.convertUserDtoToUser(userDto, role);
        return userRepository.save(user);
    }

    public User convertTokenToUser(String token) {
        String userEmail = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return userRepository.findUserByEmail(userEmail);
    }
}
