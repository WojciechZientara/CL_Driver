package pl.coderslab.Driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.dto.AnswerDto;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Answer;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Test;

import java.util.HashSet;

@Component
public class TestConverter {
    
    public TestDto convertTestToTestDto(Test test) {
        TestDto testDto = new TestDto();
        testDto.setId(test.getId());
        testDto.setAdviceId(test.getAdvice().getId());
        testDto.setAnswers(new HashSet<>());
        for (Answer answer : test.getAnswers()) {
            AnswerDto answerDto = new AnswerDto();
            answerDto.setId(answer.getId());
            answerDto.setText(answer.getText());
            answerDto.setCorrect(answer.isCorrect());
            testDto.getAnswers().add(answerDto);
        }
        return testDto;
    }
}
