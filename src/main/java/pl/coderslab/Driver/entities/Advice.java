package pl.coderslab.Driver.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@Table(name = "advices")
public class Advice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] appendix;

    @OneToOne(mappedBy = "advice", cascade = CascadeType.REMOVE)
    private Test test;

    @OneToMany(mappedBy = "advice", cascade = CascadeType.REMOVE)
    private Set<Display> displays;

    @OneToMany(mappedBy = "advice", cascade = CascadeType.REMOVE)
    private Set<Conversation> conversation;

    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }
}
