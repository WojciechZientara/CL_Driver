package pl.coderslab.Driver.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.Driver.dto.AnswerDto;
import pl.coderslab.Driver.dto.ConversationDto;
import pl.coderslab.Driver.dto.TestDto;
import pl.coderslab.Driver.entities.Answer;
import pl.coderslab.Driver.entities.Conversation;
import pl.coderslab.Driver.entities.Message;
import pl.coderslab.Driver.entities.Test;
import pl.coderslab.Driver.repositories.AdviceRepository;

import java.util.HashSet;
import java.util.Set;

@Component
public class ConversationConverter {

    @Autowired
    AdviceRepository adviceRepository;

    @Autowired
    MessageConverter messageConverter;
    
    public Conversation convertConversationDtoToConversation(ConversationDto conversationDto) {
        Conversation conversation = new Conversation();
        conversation.setId(conversationDto.getId());
        conversation.setAdvice(adviceRepository.findAdviceById(conversationDto.getAdviceId()));
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

    public ConversationDto convertConversationToConversationDtoWithMessages(Conversation conversation) {
        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setId(conversation.getId());
        conversationDto.setAdviceId(conversation.getAdvice().getId());
        conversationDto.setSubject(conversation.getSubject());
        conversationDto.setMessageDtos(new HashSet<>());
        for (Message message : conversation.getMessages()) {
            conversationDto.getMessageDtos().add(messageConverter.convertMessageToMessageDto(message));
        }
        return conversationDto;
    }
}
