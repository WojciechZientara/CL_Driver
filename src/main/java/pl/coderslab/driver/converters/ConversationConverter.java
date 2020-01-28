package pl.coderslab.driver.converters;

import org.springframework.stereotype.Component;
import pl.coderslab.driver.dto.ConversationDto;
import pl.coderslab.driver.dto.MessageDto;
import pl.coderslab.driver.entities.Advice;
import pl.coderslab.driver.entities.Conversation;
import java.util.HashSet;
import java.util.Set;

@Component
public class ConversationConverter {
    
    public Conversation convertConversationDtoToConversation(ConversationDto conversationDto,
                                                             Advice advice) {
        Conversation conversation = new Conversation();
        conversation.setId(conversationDto.getId());
        conversation.setAdvice(advice);
        conversation.setSubject(conversationDto.getSubject());
        conversation.setMessages(new HashSet<>());
        return conversation;
    }

    public ConversationDto convertConversationToConversationDtoWithoutMessages(Conversation conversation) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setId(conversation.getId());
        conversationDto.setAdviceId(conversation.getAdvice().getId());
        conversationDto.setSubject(conversation.getSubject());
        return conversationDto;
    }

    public ConversationDto convertConversationToConversationDtoWithMessages(Conversation conversation,
                                                                            Set<MessageDto> messages) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setId(conversation.getId());
        conversationDto.setAdviceId(conversation.getAdvice().getId());
        conversationDto.setSubject(conversation.getSubject());
        return conversationDto;
    }

    public Conversation applyChanges(Conversation conversation, Conversation update) {
        conversation.setSubject(update.getSubject());
        conversation.setMessages(update.getMessages());
        conversation.setAdvice(update.getAdvice());
        return conversation;
    }
}
