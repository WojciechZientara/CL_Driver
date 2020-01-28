package pl.coderslab.driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class MessageDto {

    private Long id;
    private Long conversationId;
    private Long userId;
    private String content;
    private LocalDateTime created;

}
