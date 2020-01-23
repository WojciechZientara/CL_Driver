package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.TestConverter;
import pl.coderslab.Driver.converters.TestResultConverter;
import pl.coderslab.Driver.converters.UserConverter;
import pl.coderslab.Driver.entities.*;
import pl.coderslab.Driver.repositories.*;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestConverter testConverter;

    @Autowired
    TestResultRepository testResultRepository;

    @Autowired
    TestResultConverter testResultConverter;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserConverter userConverter;

    @GetMapping("/get/{adviceId}")
    public ResponseEntity<?> getTestByAdviceId(@PathVariable Long adviceId){
        Advice advice = adviceRepository.findAdviceById(adviceId);
        List<Test> tests = testRepository.findTestWithAnswersByAdviceId(advice.getId());
        if (tests.size() == 0) {
            return ResponseEntity.ok("No test for this advice");
        } else {
            Test test = tests.get(0);
            return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
        }
    }

    @GetMapping("/resolve/{testId}/{answerId}")
    public ResponseEntity<?> resolveTest(@PathVariable Long testId, @PathVariable Long answerId,
                                     @RequestHeader(name="Authorization") String token){
        User user = userConverter.convertTokenToUser(token);
        Test test = testRepository.findTestById(testId);
        Answer answer = answerRepository.findAnswerById(answerId);
        TestResult result = testResultConverter.recordResult(user, test, answer);
        testResultRepository.save(result);
        return ResponseEntity.ok(testResultConverter.convertTestResultToTestResultDto(result));
    }

}
