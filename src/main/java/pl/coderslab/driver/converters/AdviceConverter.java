package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.Conversation;
import pl.coderslab.driver.entities.Test;
import pl.coderslab.driver.entities.User;
import java.util.ArrayList;

@Component
public class AdviceConverter {
    
    public AdviceDto convertAdviceToDto(Advice advice) {
        AdviceDto dto = new AdviceDto();
        dto.setId(advice.getId());
        dto.setAuthor(advice.getUser().getFirstName() + " " + advice.getUser().getLastName());
        dto.setTitle(advice.getTitle());
        dto.setContent(advice.getContent());
        dto.setAppendix(advice.getAppendix());
        if (advice.getTest() != null) {
            dto.setTestId(advice.getTest().getId());
        }
        dto.setCreated(advice.getCreated());
        return dto;
    }

    public Advice convertAdviceDtoToAdvice(AdviceDto adviceDto, User user, Test test) {

        Advice advice = new Advice();
        if (adviceDto.getId() != null) {
            advice.setId(adviceDto.getId());
        }
        advice.setTitle(adviceDto.getTitle());
        advice.setContent(adviceDto.getContent());
        advice.setAppendix(adviceDto.getAppendix());
        advice.setTest(test);
        advice.setUser(user);
        return advice;
    }

}
