package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.Driver.entities.Message;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class ConversationDto {

    private Long id;
    private Long adviceId;
    private String subject;
    private Set<MessageDto> messageDtos;

}
