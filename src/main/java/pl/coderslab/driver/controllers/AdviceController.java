package pl.coderslab.driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.driver.converters.AdviceConverter;
import pl.coderslab.driver.entities.Test;
import pl.coderslab.driver.jwt.JwtUserDetailsService;
import pl.coderslab.driver.repositories.TestRepository;
import pl.coderslab.driver.services.DisplayService;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.repositories.AdviceRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayService displayService;

    @Autowired
    AdviceConverter adviceConverter;

    @Autowired
    TestRepository testRepository;

    @GetMapping("/{adviceId}")
    public ResponseEntity<?> getAdvice(@PathVariable Long adviceId){

        Advice advice = adviceRepository.findAdviceById(adviceId);
        if (advice == null) {
            AdviceDto empty = new AdviceDto();
            empty.setContent("No advice with such id");
            return ResponseEntity.ok(empty);
        } else {
            return ResponseEntity.ok(adviceConverter.convertAdviceToDto(advice));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addAdvice(@RequestBody AdviceDto adviceDto,
                                     @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Test test = testRepository.findTestById(adviceDto.getTestId());
        Advice advice = adviceConverter.convertAdviceDtoToAdvice(adviceDto, user, test);
        adviceDto.setId(adviceRepository.save(advice).getId());
        return ResponseEntity.ok(adviceDto);
    }

    @PutMapping("/")
    public ResponseEntity<?> editAdvice(HttpServletRequest request, @RequestBody AdviceDto adviceDto,
                                        @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Advice originalAdvice = adviceRepository.findAdviceById(adviceDto.getId());
        if (request.isUserInRole("ROLE_ADMIN") || originalAdvice.getUser().getId() == user.getId()) {
            Test test = testRepository.findTestById(adviceDto.getTestId());
            Advice newAdvice = adviceConverter.convertAdviceDtoToAdvice(adviceDto, user, test);
            adviceRepository.save(newAdvice);
            return ResponseEntity.ok(adviceDto);
        } else {
            return ResponseEntity.badRequest().body("Not allowed to edit this advice.");
        }
    }

    @DeleteMapping("/{adviceId}")
    public ResponseEntity<?> deleteADvice(HttpServletRequest request, @PathVariable Long adviceId,
                                          @RequestHeader(name="Authorization") String token) {
        User user = jwtUserDetailsService.convertTokenToUser(token);
        Advice advice = adviceRepository.findAdviceById(adviceId);
        if (request.isUserInRole("ROLE_ADMIN") || advice.getUser().getId() == user.getId()) {
            adviceRepository.deleteById(adviceId);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Not allowed to delete this advice.");
        }
    }

    @GetMapping("/unread")
    public ResponseEntity<?> getUnreadAdvice(@RequestHeader(name="Authorization") String token){

        User user = jwtUserDetailsService.convertTokenToUser(token);
        List<Advice> newAdvices = adviceRepository.findNewAdvicesByUserId(user.getId());

        if (newAdvices.size() == 0) {
            AdviceDto empty = new AdviceDto();
            empty.setContent("No more advices for this user.");
            return ResponseEntity.ok(empty);
        } else {
            Advice newAdvice = newAdvices.get(0);
            //mark advice as seen by user -> generates a new record in the displays table in the database
            displayService.markAdviceAsSeenByUser(newAdvice, user);
            return ResponseEntity.ok(adviceConverter.convertAdviceToDto(newAdvice));
        }
    }

    @GetMapping("/mostpopular")
    public ResponseEntity<?> getMostPopularAdvices() {
        List<Advice> advices = adviceRepository.find10MostPopularAdvices();
        List<AdviceDto> dtos = new ArrayList<>();
        for (Advice advice : advices) {
            dtos.add(adviceConverter.convertAdviceToDto(advice));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/oftheweek")
    public ResponseEntity<?> getTheAdviceOfTheWeek() {
        Advice advice = adviceRepository.findTheMostPopularLastWeek();
        return ResponseEntity.ok(adviceConverter.convertAdviceToDto(advice));
    }

    @GetMapping("/newest")
    public ResponseEntity<?> getNewestAdvices() {
        List<Advice> advices = adviceRepository.find10NewestAdvices();
        List<AdviceDto> dtos = new ArrayList<>();
        for (Advice advice : advices) {
            dtos.add(adviceConverter.convertAdviceToDto(advice));
        }
        return ResponseEntity.ok(dtos);
    }

}
