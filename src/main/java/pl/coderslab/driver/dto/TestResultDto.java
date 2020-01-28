package pl.coderslab.driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class TestResultDto {

    private Long id;
    private Long testId;
    private Long answerId;
    private boolean isAnswerCorrect;

}
