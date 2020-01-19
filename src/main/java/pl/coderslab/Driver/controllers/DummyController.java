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
        user.setId(7L);
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
        user2.setId(8L);
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
        user3.setId(9L);
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
        for (long i = 0; i < 20; i++) {
            Advice advice = new Advice();
            advice.setId(i);
            advice.setTitle("Advice " + i);
            advice.setContent("Content " + i);
            advice.setCreated(LocalDateTime.now());
            advice.setDisplays(new HashSet<>());
            advice.setUser(user3);
            adviceRepository.save(advice);
        }

    }
    
}
