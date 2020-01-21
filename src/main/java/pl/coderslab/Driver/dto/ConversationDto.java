package pl.coderslab.Driver.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.Driver.deserializers.ConversationDtoDeserializer;
import pl.coderslab.Driver.entities.Message;

import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
@JsonDeserialize(using = ConversationDtoDeserializer.class)
public class ConversationDto {

    private Long id;
    private Long adviceId;
    private String subject;
    private Set<MessageDto> messageDtos;

}
