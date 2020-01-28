package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.AnswerDto;
import pl.coderslab.driver.dto.TestDto;
import pl.coderslab.driver.entities.Answer;
import pl.coderslab.driver.entities.Test;

@Component
public class AnswerConverter {
    
    public AnswerDto convertAnswerToAnswerDto(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setTestId(answer.getTest().getId());
        answerDto.setText(answer.getText());
        answerDto.setCorrect(answer.isCorrect());
        return answerDto;
    }

    public Answer convertAnswerDtoToAnswer(AnswerDto answerDto, Test test) {
        Answer answer = new Answer();
        if (answerDto.getId() != null) {
            answer.setId(answerDto.getId());
        }
        answer.setTest(test);
        answer.setText(answerDto.getText());
        answer.setCorrect(answerDto.isCorrect());
        return answer;
    }

}
