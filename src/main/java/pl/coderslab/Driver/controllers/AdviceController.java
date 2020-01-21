package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayConverter displayConverter;

    @Autowired
    AdviceConverter adviceConverter;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

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

}
