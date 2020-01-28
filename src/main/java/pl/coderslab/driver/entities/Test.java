package pl.coderslab.driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    private Advice advice;

    @NotBlank
    private String question;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private Set<Answer> answers;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private Set<TestResult> testResults;

}
