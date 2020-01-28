package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.TestConverter;
import pl.coderslab.driver.converters.TestResultConverter;
import pl.coderslab.driver.dto.TestDto;
import pl.coderslab.driver.entities.*;
import pl.coderslab.driver.jwt.JwtUserDetailsService;
import pl.coderslab.driver.repositories.*;

import javax.servlet.http.HttpServletRequest;
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
    JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/{testId}")
    public ResponseEntity<?> getTest(@PathVariable Long testId){

        Test test = testRepository.findTestById(testId);
        if (test == null) {
            TestDto empty = new TestDto();
            empty.setQuestion("No test with such id");
            return ResponseEntity.ok(empty);
        } else {
            return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addTest(HttpServletRequest request, @RequestBody TestDto testDto,
                                      @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = new Test();
        test.setAdvice(adviceRepository.findAdviceById(testDto.getAdviceId()));
        test.setQuestion(testDto.getQuestion());
        if (request.isUserInRole("ROLE_ADMIN") || test.getAdvice().getUser().getId() == user.getId()) {
            testDto.setId(testRepository.save(test).getId());
            return ResponseEntity.ok(testDto);
        } else {
            return ResponseEntity.badRequest().body("Not allowed to attach a test to this advice.");
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> editTest(HttpServletRequest request, @RequestBody TestDto testDto,
                                     @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = testRepository.findTestById(testDto.getId());
        if (request.isUserInRole("ROLE_ADMIN") || test.getAdvice().getUser().getId() == user.getId()) {
            test.setQuestion(testDto.getQuestion());
            test.setAdvice(adviceRepository.findAdviceById(testDto.getAdviceId()));
            testRepository.save(test);
            return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
        } else {
            return ResponseEntity.badRequest().body("Not allowed to modify this test.");
        }
    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<?> deleteTest(HttpServletRequest request, @PathVariable Long testId,
                                        @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = testRepository.findTestById(testId);
        if (request.isUserInRole("ROLE_ADMIN") || test.getAdvice().getUser().getId() == user.getId()) {
            testRepository.deleteById(testId);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Not allowed to delete this test.");
        }
    }

    @GetMapping("/advice/{adviceId}")
    public ResponseEntity<?> getTestByAdviceId(@PathVariable Long adviceId){
        Advice advice = adviceRepository.findAdviceById(adviceId);
        List<Test> tests = testRepository.findTestWithAnswersByAdviceId(advice.getId());
        if (tests.size() == 0) {
            TestDto empty = new TestDto();
            empty.setQuestion("No test for this advice.");
            return ResponseEntity.ok(empty);
        } else {
            Test test = tests.get(0);
            return ResponseEntity.ok(testConverter.convertTestToTestDto(test));
        }
    }

    @GetMapping("/resolve/{testId}/{answerId}")
    public ResponseEntity<?> resolveTest(@PathVariable Long testId, @PathVariable Long answerId,
                                     @RequestHeader(name="Authorization") String token){
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = testRepository.findTestById(testId);
        Answer answer = answerRepository.findAnswerById(answerId);
        TestResult result = testResultConverter.recordResult(user, test, answer);
        testResultRepository.save(result);
        return ResponseEntity.ok(testResultConverter.convertTestResultToTestResultDto(result));
    }

}
