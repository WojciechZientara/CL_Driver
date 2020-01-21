package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class TestResultDto {

    private Long id;
    private Long testId;
    private Long answerId;
    private boolean isAnswerCorrect;

}
