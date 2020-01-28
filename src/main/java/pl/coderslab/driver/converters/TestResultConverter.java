package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.TestResultDto;
import pl.coderslab.driver.entities.Answer;
import pl.coderslab.driver.entities.Test;
import pl.coderslab.driver.entities.TestResult;
import pl.coderslab.driver.entities.User;

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
