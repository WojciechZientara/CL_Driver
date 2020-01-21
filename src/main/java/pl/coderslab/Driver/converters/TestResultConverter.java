package pl.coderslab.Driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AnswerDto;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.dto.TestResultDto;
import pl.coderslab.Driver.entities.Answer;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.entities.TestResult;
import pl.coderslab.Driver.entities.User;

import java.util.HashSet;

@Component
public class TestResultConverter {
    
    public TestResultDto convertTestResultToTestResultDto(TestResult testResult) {
        TestResultDto testResultDto = new TestResultDto();
        testResultDto.setId(testResult.getId());
        testResultDto.setTestId(testResult.getTest().getId());
        testResultDto.setAnswerId(testResult.getGivenAnswer().getId());
        testResultDto.setAnswerCorrect(testResult.isAnswerCorrect());
        return testResultDto;
    }

    public TestResult recordResult(User user, Test test, Answer answer) {
        TestResult testResult = new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setGivenAnswer(answer);
        testResult.setAnswerCorrect(answer.isCorrect());
        return testResult;
    }
}
