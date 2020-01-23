package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.Driver.entities.Display;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class AdviceDto {

    private Long id;
    private String author;
    private Long userId;
    private String title;
    private String content;
    private byte[] appendix;
    private Long testId;
    private List<Long> conversationIds;
    private LocalDateTime created;





}
