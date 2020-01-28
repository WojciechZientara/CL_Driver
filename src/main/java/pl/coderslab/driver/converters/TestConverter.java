package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.TestDto;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.Test;

@Component
public class TestConverter {
    
    public TestDto convertTestToTestDto(Test test) {
        TestDto testDto = new TestDto();
        testDto.setId(test.getId());
        testDto.setAdviceId(test.getAdvice().getId());
        testDto.setQuestion(test.getQuestion());
        return testDto;
    }

    public Test convertTestDtoToTest(TestDto testDto, Advice advice) {
        Test test = new Test();
        if (testDto.getId() != null) {
            test.setId(testDto.getId());
        }
        test.setAdvice(advice);
        test.setQuestion(testDto.getQuestion());
        return test;
    }

}
