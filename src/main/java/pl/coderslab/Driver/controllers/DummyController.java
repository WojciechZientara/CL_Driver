package pl.coderslab.Driver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Display;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.AdviceRepository;
import pl.coderslab.Driver.repositories.DisplayRepository;
import pl.coderslab.Driver.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;

@RestController
public class DummyController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    DisplayRepository displayRepository;

    @GetMapping("/generateDummyData")
    public void generateDummyData(){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        //Users
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("jk@wp.pl");
        user.setPassword(passwordEncoder.encode("123"));
        user.setAdvices(new HashSet<>());
        user.setDisplays(new HashSet<>());
        user.setMessages(new HashSet<>());
        user.setTestResults(new HashSet<>());
        userRepository.save(user);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Anna");
        user2.setLastName("Nowak");
        user2.setEmail("an@wp.pl");
        user2.setPassword(passwordEncoder.encode("123"));
        user2.setAdvices(new HashSet<>());
        user2.setDisplays(new HashSet<>());
        user2.setMessages(new HashSet<>());
        user2.setTestResults(new HashSet<>());
        userRepository.save(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setFirstName("Marek");
        user3.setLastName("Marecki");
        user3.setEmail("mm@wp.pl");
        user3.setPassword(passwordEncoder.encode("123"));
        user3.setAdvices(new HashSet<>());
        user3.setDisplays(new HashSet<>());
        user3.setMessages(new HashSet<>());
        user3.setTestResults(new HashSet<>());
        userRepository.save(user3);
        
        //Advices
        Advice advice = new Advice();
        advice.setId(1L);
        advice.setTitle("Advice 1");
        advice.setContent("11111111");
        advice.setCreated(LocalDateTime.now());
        advice.setDisplays(new HashSet<>());
        advice.setUser(user3);
        adviceRepository.save(advice);

        Advice advice2 = new Advice();
        advice2.setId(2L);
        advice2.setTitle("Advice 2");
        advice2.setContent("22222222");
        advice2.setCreated(LocalDateTime.now());
        advice2.setDisplays(new HashSet<>());
        advice2.setUser(user3);
        adviceRepository.save(advice2);

        Advice advice3 = new Advice();
        advice3.setId(3L);
        advice3.setTitle("Advice 3");
        advice3.setContent("33333333");
        advice3.setCreated(LocalDateTime.now());
        advice3.setDisplays(new HashSet<>());
        advice3.setUser(user3);
        adviceRepository.save(advice3);

        //Displays

        Display display = new Display();
        display.setId(1L);
        display.setAdvice(adviceRepository.findAdviceById(1L));
        display.setUser(userRepository.findUserById(1L));
        display.setCreated(LocalDateTime.now());
        displayRepository.save(display);

    }
    
}
