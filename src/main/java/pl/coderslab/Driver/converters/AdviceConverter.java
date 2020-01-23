package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.TestResult;
import pl.coderslab.Driver.entities.User;
import pl.coderslab.Driver.repositories.TestRepository;

import java.util.ArrayList;

@Component
public class AdviceConverter {

    @Autowired
    TestRepository testRepository;
    
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
        dto.setConversationIds(new ArrayList<>());
        for (Conversation conversation : advice.getConversation()) {
            dto.getConversationIds().add(conversation.getId());
        }
        dto.setCreated(advice.getCreated());
        return dto;
    }

    public Advice convertAdviceDtoToAdvice(AdviceDto adviceDto, User user) {

        Advice advice = new Advice();
        if (adviceDto.getId() != null) {
            advice.setId(adviceDto.getId());
        }
        advice.setUser(user);
        advice.setTitle(adviceDto.getTitle());
        advice.setContent(adviceDto.getContent());
        advice.setAppendix(adviceDto.getAppendix());
        if (adviceDto.getTestId() != null) {
            advice.setTest(testRepository.findTestById(adviceDto.getTestId()));
        }
        return advice;
    }
}
