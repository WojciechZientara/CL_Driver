package pl.coderslab.driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

}
