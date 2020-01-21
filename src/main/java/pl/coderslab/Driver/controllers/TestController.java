package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.TestConverter;
import pl.coderslab.Driver.converters.TestResultConverter;
import pl.coderslab.Driver.converters.UserConverter;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.dto.TestResultDto;
import pl.coderslab.Driver.entities.*;
import pl.coderslab.Driver.jwt.JwtTokenUtil;
import pl.coderslab.Driver.repositories.*;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TestResultRepository testResultRepository;

    @Autowired
    TestConverter testConverter;

    @Autowired
    TestResultConverter testResultConverter;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @GetMapping("/get/{adviceId}")
    public TestDto getTestByAdviceId(@PathVariable Long adviceId){
        Advice advice = adviceRepository.findAdviceById(adviceId);
        List<Test> tests = testRepository.findTestWithAnswersByAdviceId(advice.getId());
        if (tests.size() == 0) {
            return null;
        } else {
            Test test = tests.get(0);
            return testConverter.convertTestToTestDto(test);
        }
    }

    @GetMapping("/resolve/{testId}/{answerId}")
    public TestResultDto resolveTest(@PathVariable Long testId, @PathVariable Long answerId,
                                     @RequestHeader(name="Authorization") String token){
        User user = userConverter.convertTokenToUser(token);
        Test test = testRepository.findTestById(testId);
        Answer answer = answerRepository.findAnswerById(answerId);
        TestResult testResult = new TestResult();
        testResult.setTest(test);
        testResult.setUser(user);
        testResult.setGivenAnswer(answer);
        testResult.setAnswerCorrect(answer.isCorrect());
        testResultRepository.save(testResult);
        return testResultConverter.convertTestResultToTestResultDto(testResult);
    }

}
