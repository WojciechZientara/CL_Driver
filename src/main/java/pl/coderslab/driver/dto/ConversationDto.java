package pl.coderslab.driver.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.driver.deserializers.ConversationDtoDeserializer;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
@JsonDeserialize(using = ConversationDtoDeserializer.class)
public class ConversationDto {

    private Long id;
    private Long adviceId;
    private String subject;

}
