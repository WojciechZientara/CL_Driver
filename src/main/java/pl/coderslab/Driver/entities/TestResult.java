package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "test_results")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Test test;

    @NotNull
    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "test", cascade = CascadeType.REMOVE)
    private Answer givenAnswer;

    private boolean isAnswerCorrect;

    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

}
