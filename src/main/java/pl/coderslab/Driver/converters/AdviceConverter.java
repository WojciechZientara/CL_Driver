package pl.coderslab.Driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AdviceDto;
import pl.coderslab.Driver.entities.Advice;
import pl.coderslab.Driver.entities.Conversation;

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
        for (Conversation conversation : advice.getConversation()) {
            dto.getConversationIds().add(conversation.getId());
        }
        dto.setCreated(advice.getCreated());
        return dto;
    }
}
