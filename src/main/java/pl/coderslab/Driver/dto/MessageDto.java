package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class MessageDto {

    private Long id;
    private Long conversationId;
    private Long userId;
    private String content;
    private LocalDateTime created;

}
