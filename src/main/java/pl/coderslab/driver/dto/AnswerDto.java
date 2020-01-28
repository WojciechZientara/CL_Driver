package pl.coderslab.driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AnswerDto {

    private Long id;
    private Long testId;
    private String text;
    private boolean isCorrect;

}
