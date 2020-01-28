package pl.coderslab.driver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.Display;
import pl.coderslab.driver.entities.User;
import pl.coderslab.driver.repositories.DisplayRepository;
import java.time.LocalDateTime;

@Service
public class DisplayService {

    @Autowired
    DisplayRepository displayRepository;

    public void markAdviceAsSeenByUser (Advice advice, User user) {
        Display display = new Display();
        display.setAdvice(advice);
        display.setUser(user);
        display.setCreated(LocalDateTime.now());
        displayRepository.save(display);
    }
}
