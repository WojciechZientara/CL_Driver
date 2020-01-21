package pl.coderslab.Driver.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    private String password;

    public JwtRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }
}
