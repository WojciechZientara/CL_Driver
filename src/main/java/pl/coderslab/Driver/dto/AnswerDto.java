package pl.coderslab.Driver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AnswerDto {

    private Long id;
    private String text;
    private boolean isCorrect;

}
