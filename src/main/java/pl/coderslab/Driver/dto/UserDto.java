package pl.coderslab.Driver.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.Driver.entities.*;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
