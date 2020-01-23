package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.dto.AnswerDto;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Answer;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.TestRepository;

import java.util.HashSet;

@Component
public class TestConverter {

    @Autowired
    TestRepository testRepository;

    @Autowired
    AdviceRepository adviceRepository;
    
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

    public Test updateTest(TestDto update) {
        Test test = testRepository.findTestById(update.getId());
        test.setAdvice(adviceRepository.findAdviceById(update.getAdviceId()));
        return test;
    }

    public Test convertTestDtoToTest(TestDto testDto) {
        Test test = new Test();
        test.setAdvice(adviceRepository.findAdviceById(testDto.getAdviceId()));
        return test;
    }
}
