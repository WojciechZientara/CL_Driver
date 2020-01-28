package pl.coderslab.driver.entities;

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
    @Column(unique = true)
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles;

}
