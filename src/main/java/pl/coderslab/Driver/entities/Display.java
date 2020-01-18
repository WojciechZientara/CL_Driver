package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "displays")
public class Display {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Advice advice;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


}
