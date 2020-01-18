package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<TestResult> testResults;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Display> displays;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Advice> advices;

}
