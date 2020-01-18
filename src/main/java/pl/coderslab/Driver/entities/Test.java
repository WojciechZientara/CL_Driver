package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;


@Getter @Setter @NoArgsConstructor
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Advice advice;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private Set<Answer> answers;

    @OneToOne(mappedBy = "test", cascade = CascadeType.REMOVE)
    private Answer givenAnswer;

    private boolean isAnswerCorrect;

}
