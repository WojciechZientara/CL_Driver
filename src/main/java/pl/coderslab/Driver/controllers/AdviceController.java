package pl.coderslab.Driver.controllers;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Display;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.DisplayRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayRepository displayRepository;

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
            AdviceDto dto = new AdviceDto();
            dto.setId(newAdvice.getId());
            dto.setAuthor(newAdvice.getUser().getFirstName() + " " + newAdvice.getUser().getLastName());
            dto.setTitle(newAdvice.getTitle());
            dto.setContent(newAdvice.getContent());
            dto.setAppendix(newAdvice.getAppendix());
            if (newAdvice.getTest() != null) {
                dto.setTestId(newAdvice.getTest().getId());
            }
            for (Conversation conversation : newAdvice.getConversation()) {
                dto.getConversationIds().add(conversation.getId());
            }
            dto.setCreated(newAdvice.getCreated());

            //mark the advice as seen
            Display display = new Display();
            display.setAdvice(newAdvice);
            display.setUser(user);
            display.setCreated(LocalDateTime.now());
            displayRepository.save(display);
            return dto;
        }
    }


}
