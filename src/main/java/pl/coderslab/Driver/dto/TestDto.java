package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.Driver.entities.Answer;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class TestDto {

    private Long id;
    private Long adviceId;
    private Set<AnswerDto> answers;

}
