package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.Driver.converters.AdviceConverter;
import pl.coderslab.Driver.converters.DisplayConverter;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayConverter displayConverter;

    @Autowired
    AdviceConverter adviceConverter;

    @GetMapping("/getNew")
    public AdviceDto getNew(){
        //Dummy user - to be changed once the authentication is implemented
        User user = userRepository.findUserById(1L);
        List<Advice> newAdvices = adviceRepository.findNewAdvicesByUserId(user.getId());

        if (newAdvices.size() == 0) {
            // return all nulls;
            return new AdviceDto();
        } else {
            Advice newAdvice = newAdvices.get(0);
            displayConverter.markAdviceAsSeenByUser(newAdvice, user);
            return adviceConverter.convertAdviceToDto(newAdvice);
        }
    }


}
