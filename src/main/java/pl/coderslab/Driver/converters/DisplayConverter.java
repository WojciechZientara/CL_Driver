package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Display;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.DisplayRepository;
import java.time.LocalDateTime;

@Component
public class DisplayConverter {

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
