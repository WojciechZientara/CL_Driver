package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Driver.converters.AdviceConverter;
import pl.coderslab.Driver.converters.DisplayConverter;
import pl.coderslab.Driver.converters.UserConverter;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.jwt.JwtTokenUtil;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    UserConverter userConverter;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayConverter displayConverter;

    @Autowired
    AdviceConverter adviceConverter;

    @GetMapping("/getNew")
    public AdviceDto getNew(@RequestHeader(name="Authorization") String token){

        User user = userConverter.convertTokenToUser(token);
        List<Advice> newAdvices = adviceRepository.findNewAdvicesByUserId(user.getId());

        if (newAdvices.size() == 0) {
            // return all nulls;
            return new AdviceDto();
        } else {
            Advice newAdvice = newAdvices.get(0);
            //mark advice as seen by user -> generates a new record in the displays table in the database
            displayConverter.markAdviceAsSeenByUser(newAdvice, user);
            return adviceConverter.convertAdviceToDto(newAdvice);
        }
    }

    @GetMapping("/get10MostPopular")
    public List<AdviceDto> getMostPopular() {
        List<Advice> advices = adviceRepository.find10MostPopularAdvices();
        List<AdviceDto> dtos = new ArrayList<>();
        for (Advice advice : advices) {
            dtos.add(adviceConverter.convertAdviceToDto(advice));
        }
        return dtos;
    }

    @GetMapping("/getTheAdviceOfTheWeek")
    public AdviceDto getTheAdviceOfTheWeek() {
        Advice advice = adviceRepository.findTheMostPopularLastWeek();
        return adviceConverter.convertAdviceToDto(advice);
    }

    @GetMapping("/get10Newest")
    public List<AdviceDto> getNewest() {
        List<Advice> advices = adviceRepository.find10NewestAdvices();
        List<AdviceDto> dtos = new ArrayList<>();
        for (Advice advice : advices) {
            dtos.add(adviceConverter.convertAdviceToDto(advice));
        }
        return dtos;
    }

    @PostMapping("/postAdvice")
    public AdviceDto postNew(@RequestBody AdviceDto adviceDto,
                             @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Advice advice = adviceConverter.convertAdviceDtoToAdvice(adviceDto, user);
        adviceDto.setId(adviceRepository.save(advice).getId());
        return adviceDto;
    }

    @PutMapping("/editAdvice")
    public AdviceDto editAdvice(@RequestBody AdviceDto adviceDto,
                             @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Advice originalAdvice = adviceRepository.findAdviceById(adviceDto.getId());
        if (originalAdvice.getUser().getId() == user.getId()) {
            Advice newAdvice = adviceConverter.convertAdviceDtoToAdvice(adviceDto, user);
            adviceRepository.save(newAdvice);
            return adviceDto;
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteAdvice/{adviceId}")
    public ResponseEntity<?> deleteADvice(@PathVariable Long adviceId,
                                          @RequestHeader(name="Authorization") String token) {
        User user = userConverter.convertTokenToUser(token);
        Advice advice = adviceRepository.findAdviceById(adviceId);
        if (advice.getUser().getId() == user.getId()) {
            adviceRepository.deleteById(adviceId);
            return ResponseEntity.ok().body("Success");
        } else {
            return ResponseEntity.badRequest().body("Not allowed");
        }
    }

}
