package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Conversation conversation;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @ManyToOne
    private User user;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }


}
