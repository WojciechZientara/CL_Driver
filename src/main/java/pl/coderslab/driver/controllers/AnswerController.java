package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.AnswerConverter;
import pl.coderslab.driver.dto.AnswerDto;
import pl.coderslab.driver.entities.*;
import pl.coderslab.driver.jwt.JwtUserDetailsService;
import pl.coderslab.driver.repositories.AnswerRepository;
import pl.coderslab.driver.repositories.TestRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;
    
    @Autowired
    AnswerConverter answerConverter;
    
    @Autowired
    TestRepository testRepository;

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @GetMapping("/{answerId}")
    public ResponseEntity<?> getAnswer(@PathVariable Long answerId){

        Answer answer = answerRepository.findAnswerById(answerId);
        if (answer == null) {
            AnswerDto empty = new AnswerDto();
            empty.setText("No answer with such id");
            return ResponseEntity.ok(empty);
        } else {
            return ResponseEntity.ok(answerConverter.convertAnswerToAnswerDto(answer));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addAnswer(HttpServletRequest request, @RequestBody AnswerDto answerDto,
                                      @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = testRepository.findTestById(answerDto.getTestId());
        Answer answer = answerConverter.convertAnswerDtoToAnswer(answerDto, test);
        if (request.isUserInRole("ROLE_ADMIN") || answer.getTest().getAdvice().getUser().getId() == user.getId()) {
            answerDto.setId(answerRepository.save(answer).getId());
            return ResponseEntity.ok(answerDto);
        } else {
            return ResponseEntity.badRequest().body("Not allowed to attach an answer to this test.");
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> editAnswer(HttpServletRequest request, @RequestBody AnswerDto answerDto,
                                     @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Answer answer = answerRepository.findAnswerById(answerDto.getId());
        Test test = testRepository.findTestById(answerDto.getTestId());
        if (request.isUserInRole("ROLE_ADMIN") || answer.getTest().getAdvice().getUser().getId() == user.getId()) {
            answer.setCorrect(answerDto.isCorrect());
            answer.setText(answerDto.getText());
            answer.setTest(test);
            answerRepository.save(answer);
            return ResponseEntity.ok(answerConverter.convertAnswerToAnswerDto(answer));
        } else {
            return ResponseEntity.badRequest().body("Not allowed to modify this answer.");
        }
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<?> deleteAnswer(HttpServletRequest request, @PathVariable Long answerId,
                                        @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Answer answer = answerRepository.findAnswerById(answerId);
        if (request.isUserInRole("ROLE_ADMIN") || answer.getTest().getAdvice().getUser().getId() == user.getId()) {
            answerRepository.deleteById(answerId);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Not allowed to delete this answer.");
        }
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<?> getAnswersByTestId(@PathVariable Long testId){
        List<Answer> answers = answerRepository.findAnswersByTestId(testId);
        List<AnswerDto> dtos = new ArrayList<>();
        if (answers.size() == 0) {
            AnswerDto empty = new AnswerDto();
            empty.setText("No answers for this test.");
            dtos.add(empty);
            return ResponseEntity.ok(dtos);
        } else {
            for (Answer answer : answers) {
                dtos.add(answerConverter.convertAnswerToAnswerDto(answer));
            }
            return ResponseEntity.ok(dtos);
        }
    }

}
